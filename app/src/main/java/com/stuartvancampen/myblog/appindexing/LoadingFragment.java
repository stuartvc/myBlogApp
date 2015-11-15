package com.stuartvancampen.myblog.appindexing;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stuartvancampen.myblog.R;

/**
 * Created by Stuart on 14/11/2015.
 */
public class LoadingFragment extends Fragment {

    private static final String TAG = LoadingFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loading_fragment, container, false);

        return view;
    }

    public void loadingFinished() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }
}
