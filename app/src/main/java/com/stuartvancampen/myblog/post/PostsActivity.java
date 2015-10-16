package com.stuartvancampen.myblog.post;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.stuartvancampen.myblog.R;
import com.stuartvancampen.myblog.user.models.User;
import com.stuartvancampen.myblog.util.MyActivity;

/**
 * Created by Stuart on 15/10/2015.
 */
public class PostsActivity extends MyActivity {

    private User mUser;

    public static Intent create(Context context, User user) {
        Intent startIntent = new Intent(context, PostsActivity.class);
        startIntent.putExtra("user", user.toString());
        return startIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("user", mUser.toString());
    }

    @Override
    protected void onLoadInstanceState(Bundle savedInstanceState) {
        super.onLoadInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("user")) {
                mUser = new User(savedInstanceState.getString("user"));
            }
        }
        else {
            Intent intent = getIntent();
            if (intent.hasExtra("user")) {
                mUser = new User(intent.getStringExtra("user"));
            }
        }
    }

    @Override
    protected Fragment constructFragment(Bundle savedInstanceState) {
        return PostsFragment.create(mUser);
    }
}
