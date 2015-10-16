package com.stuartvancampen.myblog.user.profile;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.stuartvancampen.myblog.user.models.UserPreview;
import com.stuartvancampen.myblog.util.MyActivity;

/**
 * Created by Stuart on 14/10/2015.
 */
public class UserProfileActivity extends MyActivity {

    private UserPreview mUserPreview;

    public static Intent create(Context context, UserPreview userPreview) {
        Intent startIntent = new Intent(context, UserProfileActivity.class);
        startIntent.putExtra("user", userPreview.toString());
        return startIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected Fragment constructFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("user")) {
                mUserPreview = new UserPreview(savedInstanceState.getString("user"));
            }
        }
        else {
            Intent intent = getIntent();
            if (intent.hasExtra("user")) {
                mUserPreview = new UserPreview(intent.getStringExtra("user"));
            }
        }
        return UserProfileFragment.create(mUserPreview);
    }
}
