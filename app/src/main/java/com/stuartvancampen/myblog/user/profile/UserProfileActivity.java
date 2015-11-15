package com.stuartvancampen.myblog.user.profile;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;
import com.stuartvancampen.myblog.R;
import com.stuartvancampen.myblog.user.models.User;
import com.stuartvancampen.myblog.user.models.UserPreview;
import com.stuartvancampen.myblog.util.MyActivity;
import com.stuartvancampen.myblog.util.RemoteJsonObjectLoader;

import java.net.URI;

/**
 * Created by Stuart on 14/10/2015.
 */
public class UserProfileActivity extends MyActivity {

    static final String APP_URL = "android-app://com.stuartvancampen.myblog/https/dry-tor-1032.herokuapp.com/users/";
    static final String WEB_URL = "https://dry-tor-1032.herokuapp.com/users/";

    private static final String EXTRA_USER = "user";
    private User mUser;

    public static void start(Activity activity, UserPreview userPreview) {
        start(activity, userPreview.getFullUserUrl(activity));
    }

    public static void start(Activity activity, String url) {
        createRemoteUserLoader(activity, url).startLoad();
    }

    private static RemoteJsonObjectLoader<User> createRemoteUserLoader(Activity activity, String url) {
        return new RemoteJsonObjectLoader<User>(activity, User.class, getEmptyStartIntent(activity), url, EXTRA_USER);
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
    protected Fragment constructFragment() {
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

    @Override
    protected boolean allowExternalLaunch() {
        return true;
    }

    @Override
    protected RemoteJsonObjectLoader getRemoteLoader(String url) {
        return createRemoteUserLoader(this, url);
    }

    @Override
    protected boolean enableAppIndexing() {
        return mUser != null;
    }

    @Override
    protected Action buildViewAction() {
        /*return Action.newAction(Action.TYPE_VIEW,
                mUser.getName(),
                Uri.parse(WEB_URL + mUser.getId()),
                Uri.parse(APP_URL + mUser.getId()));
        */

        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(new Thing.Builder()
                        .setName(mUser.getName())
                        .setDescription(mUser.getEmail())
                        .setId(Uri.parse(WEB_URL + mUser.getId()).toString())
                        .setUrl(Uri.parse(APP_URL + mUser.getId()))
                        .put("image", "http://weknowyourdreams.com/images/dog/dog-07.jpg")
                        .build())
                .build();
    }
}
