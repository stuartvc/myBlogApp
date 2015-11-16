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
public class LoginFragment extends Fragment implements OnLoginCallback {
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new ASyncLoginRequest(this, getActivity());
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

        new ASyncLoginRequest(email, password, this, getActivity());
    }

    @Override
    public void onLoginComplete(boolean success) {
        if (success) {
            getActivity().startActivity(UsersActivity.create(getActivity()));
        }
        else {
            Toast.makeText(getActivity(), R.string.authentication_error, Toast.LENGTH_LONG).show();
        }
    }
}
