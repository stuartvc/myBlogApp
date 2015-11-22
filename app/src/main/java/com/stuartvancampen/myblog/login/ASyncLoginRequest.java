package com.stuartvancampen.myblog.login;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;

import com.stuartvancampen.myblog.R;
import com.stuartvancampen.myblog.session.AuthPreferences;
import com.stuartvancampen.myblog.session.Session;
import com.stuartvancampen.myblog.user.models.User;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Stuart on 15/11/2015.
 */
public class ASyncLoginRequest extends AsyncTask<String, String, JsonReader> {

    private static final String TAG = ASyncLoginRequest.class.getSimpleName();
    private final OnLoginCallback mListener;

    public ASyncLoginRequest(String username, String password, OnLoginCallback listener, Context context) {
        mListener = listener;
        String basicAuth = "Basic " + Base64.encodeToString((username + ":" + password).getBytes(), Base64.DEFAULT);
        String url = context.getString(R.string.base_url) + context.getString(R.string.login_url);
        execute(basicAuth, url);
    }

    public ASyncLoginRequest(OnLoginCallback listener, Context context) {
        mListener = listener;
        String auth_token = AuthPreferences.get().getAuthToken();
        if (auth_token == null) {
            Log.d(TAG, "no auth key in preferencs");
            listener.onLoginComplete(false);
            this.cancel(true);
            return;
        }
        String tokenAuth = "Token " + auth_token;
        String url = context.getString(R.string.base_url) + context.getString(R.string.login_verification_url);
        execute(tokenAuth, url);
    }

    @Override
    protected JsonReader doInBackground(String... params) {
        try {
            String auth = params[0];
            URL url = new URL(params[1]);
            Log.d("LoginFragment", "loading url:" + url);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            conn.setRequestProperty("Authorization", auth);

            conn.setRequestMethod("GET");

            // read the response
            Log.d(TAG, "Response Code: " + conn.getResponseCode());
            InputStreamReader inReader = new InputStreamReader(conn.getInputStream());
            return new JsonReader(inReader);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JsonReader reader) {
        boolean loginSuccess = false;
        String authToken = null;

        if (reader == null) {
            mListener.onLoginComplete(false);
            return;
        }

        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name == null) {
                    reader.skipValue();
                }
                else if (name.equals("token")) {
                    authToken = reader.nextString();
                    loginSuccess = true;
                }
                else if (name.equals("login")) {
                    reader.skipValue();
                    loginSuccess = true;
                }
                else if (name.equals("user")) {
                    Session session = Session.getInstance();
                    session.updateUser(new User(reader));
                }
                else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            loginSuccess = false;
        } catch (ProtocolException e) {
            e.printStackTrace();
            loginSuccess = false;
        } catch (IOException e) {
            e.printStackTrace();
            loginSuccess = false;
        }

        if (loginSuccess) {
            Log.d("LoginFragment", "token:" + authToken);
            if (authToken != null) {
                AuthPreferences.get().setAuthToken(authToken);
            }

            mListener.onLoginComplete(true);
        }
        else {
            Log.d(TAG, "login attempt failed");
            AuthPreferences.get().clearAuthToken();
            mListener.onLoginComplete(false);
        }
    }
}
