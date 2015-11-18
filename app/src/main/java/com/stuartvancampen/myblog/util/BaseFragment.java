package com.stuartvancampen.myblog.util;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by Stuart on 15/11/2015.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }
}
