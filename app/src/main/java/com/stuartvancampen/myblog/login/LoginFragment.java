package com.stuartvancampen.myblog.login;

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

import com.stuartvancampen.myblog.R;
import com.stuartvancampen.myblog.user.list.UsersActivity;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Stuart on 17/10/2015.
 */
public class LoginFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        final EditText email = (EditText) view.findViewById(R.id.user_email);
        final EditText password = (EditText) view.findViewById(R.id.user_password);

        TextView loginButton = (TextView) view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(email.getText().toString(), password.getText().toString());
            }
        });

        TextView signUpButton = (TextView) view.findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(SignUpActivity.create(getActivity()));
            }
        });

        return view;
    }

    private void login(String email, String password) {
        Toast.makeText(getActivity(),
                "email:" + email + ", password:" + password,
                Toast.LENGTH_LONG)
             .show();

        new AsyncTask<String, String, String>() {

            @Override
            protected String doInBackground(String... params) {
                try {
                    String basicAuth = params[0];
                    URL url = new URL(getString(R.string.base_url) + getString(R.string.login_url));
                    Log.d("LoginFragment", "loading url:" + url);
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

                    basicAuth = "Basic " + basicAuth;
                    conn.setRequestProperty("Authorization", basicAuth);

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
        }.execute(Base64.encodeToString((email + ":" + password).getBytes(), Base64.DEFAULT));
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
