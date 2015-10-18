package com.stuartvancampen.myblog;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stuartvancampen.myblog.user.list.UsersActivity;

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
 * Created by Stuart on 17/10/2015.
 */
public class SignUpFragment extends Fragment {

    private static final String TAG = SignUpFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_fragment, container, false);

        final EditText name = (EditText) view.findViewById(R.id.sign_up_name);
        final EditText email = (EditText) view.findViewById(R.id.sign_up_email);
        final EditText password = (EditText) view.findViewById(R.id.sign_up_password);

        TextView submit = (TextView) view.findViewById(R.id.submit_sign_up);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser(name.getText().toString(), email.getText().toString(), password.getText().toString());
            }
        });

        return view;
    }

    private void signUpUser(String name, String email, String password) {

        HashMap<String, String> newUser = new HashMap<>();
        newUser.put("name", name);
        newUser.put("email", email);
        newUser.put("password", password);
        JSONObject postBody = new JSONObject();
        try {
            postBody.put("user", new JSONObject(newUser));
        } catch (JSONException e) {
            Log.d(TAG, "jsonException");
        }

        new AsyncTask<String, String, String>() {

            @Override
            protected String doInBackground(String... params) {
                try {
                    String postBody = params[0];
                    URL url = new URL(getString(R.string.base_url) + getString(R.string.users_url));
                    Log.d("LoginFragment", "loading url:" + url);
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");

                    DataOutputStream wr = new DataOutputStream( conn.getOutputStream());
                    wr.write( postBody.getBytes() );

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
                onLoginAttemptComplete(token);
            }
        }.execute(postBody.toString());
    }

    private void onLoginAttemptComplete(String token) {
        if (token == null) {
            Toast.makeText(getActivity(), R.string.authentication_error, Toast.LENGTH_LONG).show();
        }
        else {
            Log.d("LoginFragment", "token:" + token);
            SharedPreferences settings = getActivity().getSharedPreferences("auth", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("auth_token", token);
            editor.commit();

            getActivity().startActivity(UsersActivity.create(getActivity()));
        }
    }
}
