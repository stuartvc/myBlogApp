package com.stuartvancampen.myblog;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.stuartvancampen.myblog.util.MyActivity;


public class MainActivity extends MyActivity {


    public static Intent create(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected Fragment constructFragment(Bundle savedInstanceState) {
        return new LoginFragment();
    }
}
