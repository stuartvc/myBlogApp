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
import android.view.Menu;
import android.view.MenuItem;

import com.stuartvancampen.myblog.MainActivity;
import com.stuartvancampen.myblog.R;

/**
 * Created by Stuart on 13/10/2015.
 */
public abstract class MyActivity extends AppCompatActivity {

    public static final String FRAGMENT_TAG = "FRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onLoadInstanceState(savedInstanceState);

        setContentView(getLayoutId());

        FragmentManager fragmentManager = getFragmentManager();

        Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);

        if (fragment == null) {
            fragment = constructFragment(savedInstanceState);
        }

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
                    .commit();
        }

        IntentFilter logoutIntent = new IntentFilter();
        logoutIntent.addAction("com.stuartvancampen.myblog.logout");
        registerReceiver(mOnLogoutReceiver, logoutIntent);


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

    protected abstract Fragment constructFragment(Bundle savedInstanceState);


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
            SharedPreferences settings = getSharedPreferences("auth", 0);
            settings.edit().remove("auth_token").commit();
            startActivity(MainActivity.create(this));
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("com.stuartvancampen.myblog.logout");
            sendBroadcast(broadcastIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
