package com.stuartvancampen.myblog.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Stuart on 25/09/2015.
 */
public class MyLoader<T extends MyList> {

    private static final String TAG = MyLoader.class.getSimpleName();

    public enum STATE {
        STARTED,
        FINISHED,
        FAILED
    }

    public interface MyLoaderCallbacks<T extends MyList> {
        void onFinishLoad(T result);

        void onFailed();

        void onStarted();

        String getUrl();
    }

    private Class<T> mClazz;
    private Handler mHandler;
    private Thread mThread;
    private MyLoaderCallbacks<T> mLoaderListener;
    final AtomicBoolean mRunning = new AtomicBoolean();
    boolean mFinished;
    boolean mReset;

    public MyLoader(MyLoaderCallbacks<T> loaderListener, Class<T> clazz) {
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

    private class MyHandler extends Handler {

        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            T list = (T) msg.obj;

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
    }

    private class MyRunnable implements Runnable {

        @Override
        public void run() {
            STATE state;
            mHandler.obtainMessage(STATE.STARTED.ordinal()).sendToTarget();

            T list = null;
            try {
                list = mClazz.newInstance();

                URL url = new URL(mLoaderListener.getUrl());
                Log.d(TAG, "url:" + url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                // read the response
                System.out.println("Response Code: " + conn.getResponseCode());
                InputStreamReader inReader = new InputStreamReader(conn.getInputStream());
                JsonReader reader = new JsonReader(inReader);
                list.loadList(reader);
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

            mHandler.obtainMessage(state.ordinal(), list).sendToTarget();
        }
    }
}
