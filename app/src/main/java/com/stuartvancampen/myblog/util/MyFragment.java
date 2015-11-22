package com.stuartvancampen.myblog.util;

import android.os.Bundle;

/**
 * Created by Stuart on 25/09/2015.
 */
public abstract class MyFragment<T extends MyObject, LT extends MyList<T>> extends BaseFragment implements OnItemClickListener {

    protected MyAdapter mAdapter;
    protected MyLoader mLoader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        mAdapter = createAdapter();
        mLoader = createLoader();

        mLoader.startLoad();
    }

    public abstract MyLoader createLoader();
    public abstract MyAdapter createAdapter();

    public void onDataLoaded() {
    }

    public abstract String getUrlPath();
}
