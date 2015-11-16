package com.stuartvancampen.myblog.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;

import com.stuartvancampen.myblog.R;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Stuart on 15/11/2015.
 */
public class ASyncLoginRequest extends AsyncTask<String, String, String> {

    private final OnLoginCallback mListener;
    private final SharedPreferences mSettings;

    public ASyncLoginRequest(String username, String password, OnLoginCallback listener, Context context) {
        mListener = listener;
        mSettings = context.getSharedPreferences("auth", 0);
        String basicAuth = "Basic " + Base64.encodeToString((username + ":" + password).getBytes(), Base64.DEFAULT);
        String url = context.getString(R.string.base_url) + context.getString(R.string.login_url);
        execute(basicAuth, url);
    }

    public ASyncLoginRequest(OnLoginCallback listener, Context context) {
        mListener = listener;
        mSettings = context.getSharedPreferences("auth", 0);
        String auth_token = mSettings.getString("auth_token", null);
        if (auth_token == null) {
            listener.onLoginComplete(false);
        }
        String tokenAuth = "Token " + auth_token;
        String url = context.getString(R.string.base_url) + context.getString(R.string.login_verification_url);
        execute(tokenAuth, url);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String auth = params[0];
            URL url = new URL(params[1]);
            Log.d("LoginFragment", "loading url:" + url);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            conn.setRequestProperty("Authorization", auth);

            conn.setRequestMethod("GET");

            // read the response
            System.out.println("Response Code: " + conn.getResponseCode());
            InputStreamReader inReader = new InputStreamReader(conn.getInputStream());
            JsonReader reader = new JsonReader(inReader);

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name == null) {
                    reader.skipValue();
                }
                else if (name.equals("token")) {
                    return reader.nextString();
                }
                else if (name.equals("login")) {
                    return reader.nextString();
                }
                else {
                    reader.skipValue();
                }
            }
            reader.endObject();
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
    protected void onPostExecute(String token) {
        if (token != null) {
            Log.d("LoginFragment", "token:" + token);
            if (!token.equals("success")) {
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString("auth_token", token);
                editor.apply();
            }

            mListener.onLoginComplete(true);
        }
        else {
            clearAuthSettings();
            mListener.onLoginComplete(false);
        }
    }

    private void clearAuthSettings() {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.clear();
        editor.apply();
    }
}
