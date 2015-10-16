package com.stuartvancampen.myblog.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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

    }

    protected int getLayoutId() {
        return R.layout.my_activity;
    }

    protected void onLoadInstanceState(Bundle savedInstanceState) {
    }

    protected abstract Fragment constructFragment(Bundle savedInstanceState);
}
