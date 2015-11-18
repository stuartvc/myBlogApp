package com.stuartvancampen.myblog.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.stuartvancampen.myblog.login.LoginActivity;
import com.stuartvancampen.myblog.R;
import com.stuartvancampen.myblog.session.AuthPreferences;
import com.stuartvancampen.myblog.session.Session;

/**
 * Created by Stuart on 13/10/2015.
 */
public abstract class MyActivity extends AppCompatActivity {

    public static final String FRAGMENT_TAG = "FRAGMENT_TAG";
    private static final String TAG = MyActivity.class.getSimpleName();

    private boolean mExternalLaunch;
    private RemoteJsonObjectLoader mRemoteLoader;
    private GoogleApiClient mClient;
    private boolean mAppIndexingStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        mAppIndexingStarted = false;

        mExternalLaunch = checkForExternalLaunch(getIntent());

        if (!mExternalLaunch) {
            onLoadInstanceState(savedInstanceState);
        }

        setContentView(getLayoutId());

        insertFragment(mExternalLaunch);

        IntentFilter logoutIntent = new IntentFilter();
        logoutIntent.addAction("com.stuartvancampen.myblog.logout");
        registerReceiver(mOnLogoutReceiver, logoutIntent);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mExternalLaunch) {
            startAppIndex();
        }
    }

    @Override
    protected void onStop() {
        if (!mExternalLaunch) {
            stopAppIndex();
        }
        super.onStop();
    }

    private void insertFragment(boolean showLoadingFragment) {
        FragmentManager fragmentManager = getFragmentManager();

        Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);

        if (fragment == null) {
            if (showLoadingFragment) {
                fragment = new LoadingFragment();
                mRemoteLoader.associateWithFragmentAndStartLoading((LoadingFragment) fragment);
            }
            else {
                fragment = constructFragment();
            }
        }

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mOnLogoutReceiver);
    }

    private BroadcastReceiver mOnLogoutReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    protected int getLayoutId() {
        return R.layout.my_activity;
    }

    protected void onLoadInstanceState(Bundle savedInstanceState) {
    }

    protected abstract Fragment constructFragment();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.menu_logout) {
            closeOptionsMenu();
            AuthPreferences.get().clearAuthToken();

            startActivity(LoginActivity.create(this));

            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("com.stuartvancampen.myblog.logout");
            sendBroadcast(broadcastIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected boolean allowExternalLaunch() {
        return false;
    }

    protected RemoteJsonObjectLoader getRemoteLoader(String url) {
        Log.e(TAG, "startRemoteLoadObject is a stub, must be overridden");
        return null;
    }

    protected boolean checkForExternalLaunch(Intent intent) {
        String action = intent.getAction();
        String data = intent.getDataString();
        if (allowExternalLaunch() && Intent.ACTION_VIEW.equals(action) && data != null) {
            mRemoteLoader = getRemoteLoader(data);
            return true;
        }
        return false;

    }

    private void startAppIndex() {
        if (enableAppIndexing() && !mAppIndexingStarted) {
            mAppIndexingStarted = true;
            // Connect your client
            mClient.connect();

            // Construct the Action performed by the user
            Action viewAction = buildViewAction();

            // Call the App Indexing API start method after the view has completely rendered
            AppIndex.AppIndexApi.start(mClient, viewAction);
        }
    }

    private void stopAppIndex() {
        if (enableAppIndexing() && mAppIndexingStarted) {
            // Call end() and disconnect the client
            Action viewAction = buildViewAction();
            AppIndex.AppIndexApi.end(mClient, viewAction);
            mClient.disconnect();
            mAppIndexingStarted = false;
        }
    }

    protected boolean enableAppIndexing() {
        return false;
    }

    protected Action buildViewAction() {
        return null;
    }
}
