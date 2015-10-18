package com.stuartvancampen.myblog.user.list;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.stuartvancampen.myblog.util.MyActivity;

/**
 * Created by Stuart on 13/10/2015.
 */
public class UsersActivity extends MyActivity {

    public static Intent create(Context context) {
        return new Intent(context, UsersActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected Fragment constructFragment(Bundle savedInstanceState) {
        return UsersFragment.create();
    }
}
