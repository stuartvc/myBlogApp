package com.stuartvancampen.myblog.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.JsonReader;
import android.util.Log;

import com.stuartvancampen.myblog.login.LoginActivity;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Stuart on 25/09/2015.
 */
public class MyLoader<T extends SerializableObject> {

    private static final String TAG = MyLoader.class.getSimpleName();

    public enum STATE {
        STARTED,
        FINISHED,
        FAILED
    }

    public interface MyLoaderCallbacks<T extends SerializableObject> {
        void onFinishLoad(T result);

        void onFailed();

        void onStarted();

        String getUrl();
    }

    protected final Context mContext;
    private Class<T> mClazz;
    private Handler mHandler;
    private Thread mThread;
    private MyLoaderCallbacks<T> mLoaderListener;
    final AtomicBoolean mRunning = new AtomicBoolean();

    public MyLoader(Context context, MyLoaderCallbacks<T> loaderListener, Class<T> clazz) {
        mContext = context;
        mClazz = clazz;
        mLoaderListener = loaderListener;
        mHandler = new MyHandler(Looper.getMainLooper());
        mRunning.set(false);
    }

    public void startLoad() {
        if (!mRunning.get()) {
            mRunning.set(true);
            mThread = new Thread(new MyRunnable());
            mThread.start();
        }
    }

    protected void setListener(MyLoaderCallbacks<T> listener) {
        mLoaderListener = listener;
    }
    

    private class MyHandler extends Handler {

        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            T list = null;
            try {
                list = mClazz.cast(msg.obj);
            } catch(ClassCastException e) {
                Log.e(TAG, "classCastError handleMessage");
            }

            if (mLoaderListener != null) {
                if (msg.what == STATE.FINISHED.ordinal()) {
                    mLoaderListener.onFinishLoad(list);
                    mRunning.set(false);
                } else if (msg.what == STATE.STARTED.ordinal()) {
                    mLoaderListener.onStarted();
                } else if (msg.what == STATE.FAILED.ordinal()) {
                    mLoaderListener.onFailed();
                } else {
                    super.handleMessage(msg);
                }
            }
            else {
                super.handleMessage(msg);
            }
        }
    }

    private class MyRunnable implements Runnable {

        @Override
        public void run() {
            STATE state;
            mHandler.obtainMessage(STATE.STARTED.ordinal()).sendToTarget();

            T serializableObject = null;
            try {
                URL url = new URL(mLoaderListener.getUrl());
                Log.d(TAG, "url:" + url);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                SharedPreferences settings = mContext.getSharedPreferences("auth", 0);
                String auth_token = settings.getString("auth_token", null);
                if (auth_token == null) {
                    mContext.startActivity(LoginActivity.create(mContext));
                    mHandler.obtainMessage(STATE.FAILED.ordinal(), null).sendToTarget();
                    return;
                }

                String authHeader = "Token " + auth_token;
                conn.setRequestProperty("Authorization", authHeader);

                // read the response
                System.out.println("Response Code: " + conn.getResponseCode());
                InputStreamReader inReader = new InputStreamReader(conn.getInputStream());
                JsonReader reader = new JsonReader(inReader);
                serializableObject = loadSerializableObject(mClazz, reader);
                state = STATE.FINISHED;
            }
            catch (IOException exception) {
                state = STATE.FAILED;
                Log.d(TAG, "ioException" + exception.toString());
            }
            catch (Exception e) {
                e.printStackTrace();
                state = STATE.FAILED;
            }

            mHandler.obtainMessage(state.ordinal(), serializableObject).sendToTarget();
        }
    }

    private T loadSerializableObject(Class<T> clazz, JsonReader reader) {
        T object = null;
        boolean foundObject = false;
        try {
            object = clazz.newInstance();

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name == null) {
                    reader.skipValue();
                }
                else if (name.equals(object.getRootJson())) {
                    object.loadFromJson(reader);
                    foundObject = true;
                }
                else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        } catch (InstantiationException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
        finally {
            if (!foundObject) {
                Log.e(TAG, "could not find json object for SerializableObject:" + clazz.getSimpleName());
            }
        }

        return object;
    }
}
