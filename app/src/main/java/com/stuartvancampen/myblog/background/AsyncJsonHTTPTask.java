package com.stuartvancampen.myblog.background;

import android.util.JsonReader;
import android.util.Log;

import com.stuartvancampen.myblog.login.LoginActivity;
import com.stuartvancampen.myblog.session.AuthPreferences;
import com.stuartvancampen.myblog.util.SerializableObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Stuart on 18/11/2015.
 *
 * JsonPostTask that handles asynchronous http requests to server
 */
public class AsyncJsonHTTPTask<Result extends SerializableObject> extends FragmentAsyncTask<Result> {

    private static final String TAG = AsyncJsonHTTPTask.class.getSimpleName();

    private final Class<Result> mClazz;
    private final SerializableObject mPostObject;
    private final HttpVerb mHttpVerb;
    private final String mUrl;

    public enum HttpVerb {
            //OPTIONS,
            GET,
            //HEAD,
            POST,
            PUT,
            DELETE,
            //TRACE
            // Note: we don't allow users to specify "CONNECT"
    };

    public AsyncJsonHTTPTask(Class<Result> clazz,
                             SerializableObject postObject,
                             String url,
                             HttpVerb httpVerb) {
        super();
        mClazz = clazz;
        mPostObject = postObject;
        mUrl = url;
        mHttpVerb = httpVerb;
    }

    @Override
    protected Result doInBackground(Void... params) {
        Result serializableObject = null;
        try {
            URL url = new URL(mUrl);
            Log.d(TAG, "loading url:" + mUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod(mHttpVerb.name());
            conn.setRequestProperty("Content-Type", "application/json");


            String auth_token = AuthPreferences.get().getAuthToken();
            if (auth_token == null) {
                return null;
            }

            String authHeader = "Token " + auth_token;
            conn.setRequestProperty("Authorization", authHeader);

            DataOutputStream wr = new DataOutputStream( conn.getOutputStream());
            JSONObject wrappedJson = new JSONObject();
            wrappedJson.put(mPostObject.getRootJson(), mPostObject.loadToJson());
            Log.d(TAG, "sending:" + wrappedJson.toString());
            wr.write(wrappedJson.toString().getBytes());

            // read the response
            Log.d(TAG, "Response Code: " + conn.getResponseCode());
            InputStreamReader inReader = new InputStreamReader(conn.getInputStream());
            JsonReader reader = new JsonReader(inReader);
            serializableObject = loadSerializableObject(mClazz, reader);
        } catch (MalformedURLException e) {
            Log.e(TAG, "bad url");
            e.printStackTrace();
        } catch (ProtocolException e) {
            Log.e(TAG, "bad protocol");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "IOException");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e(TAG, "JSONException");
            e.printStackTrace();
        }
        return serializableObject;
    }

    private Result loadSerializableObject(Class<Result> clazz, JsonReader reader) {
        Result object = null;
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
