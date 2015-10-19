package com.stuartvancampen.myblog.user.profile;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.stuartvancampen.myblog.R;
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("user", mUserPreview.toString());
    }

    @Override
    protected Fragment constructFragment(Bundle savedInstanceState) {
        return UserProfileFragment.create(mUserPreview);
    }

    @Override
    protected void onLoadInstanceState(Bundle savedInstanceState) {
        super.onLoadInstanceState(savedInstanceState);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_edit_profile) {
            return true;
        }
        else if (id == R.id.menu_delete_user) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
