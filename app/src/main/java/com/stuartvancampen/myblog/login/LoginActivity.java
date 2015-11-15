package com.stuartvancampen.myblog.login;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.stuartvancampen.myblog.util.MyActivity;


public class LoginActivity extends MyActivity {


    public static Intent create(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected Fragment constructFragment() {
        return new LoginFragment();
    }
}
