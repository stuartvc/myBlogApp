package com.stuartvancampen.myblog.login;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.stuartvancampen.myblog.util.MyActivity;

/**
 * Created by Stuart on 17/10/2015.
 */
public class SignUpActivity extends MyActivity {

    public static Intent create(Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    @Override
    protected Fragment constructFragment() {
        return new SignUpFragment();
    }
}
