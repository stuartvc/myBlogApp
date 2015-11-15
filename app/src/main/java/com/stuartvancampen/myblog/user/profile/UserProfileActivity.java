package com.stuartvancampen.myblog.user.profile;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.stuartvancampen.myblog.R;
import com.stuartvancampen.myblog.user.models.User;
import com.stuartvancampen.myblog.user.models.UserPreview;
import com.stuartvancampen.myblog.util.MyActivity;
import com.stuartvancampen.myblog.util.RemoteJsonObjectLoader;

/**
 * Created by Stuart on 14/10/2015.
 */
public class UserProfileActivity extends MyActivity {

    private static final String EXTRA_USER = "user";
    private User mUser;

    public static void start(Context context, UserPreview userPreview) {
        start(context, userPreview.getFullUserUrl(context));
    }

    public static void start(Context context, String userUrl) {
        new RemoteJsonObjectLoader<User>(context, User.class, getEmptyStartIntent(context), userUrl, EXTRA_USER);
    }

    public static void start(Context context, User user) {
        Intent startIntent = getEmptyStartIntent(context);
        startIntent.putExtra(EXTRA_USER, user.toString());
        context.startActivity(startIntent);
    }

    private static Intent getEmptyStartIntent(Context context) {
        return new Intent(context, UserProfileActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mUser != null) {
            outState.putString(EXTRA_USER, mUser.toString());
        }
    }

    @Override
    protected Fragment constructFragment(Bundle savedInstanceState) {
        return UserProfileFragment.create(mUser);
    }

    @Override
    protected void onLoadInstanceState(Bundle savedInstanceState) {
        super.onLoadInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(EXTRA_USER)) {
                mUser = new User(savedInstanceState.getString(EXTRA_USER));
            }
        }
        else {
            Intent intent = getIntent();
            if (intent.hasExtra(EXTRA_USER)) {
                mUser = new User(intent.getStringExtra(EXTRA_USER));
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
