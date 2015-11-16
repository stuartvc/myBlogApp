package com.stuartvancampen.myblog.util;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Stuart on 14/11/2015.
 */
public class RemoteJsonObjectLoader<T extends SerializableObject> extends MyLoader<T> implements MyLoader.MyLoaderCallbacks<T> {

    private final Intent mStartIntent;
    private final String mUrl;
    private final String mExtraKey;
    private LoadingFragment mLoadingFragment;

    public RemoteJsonObjectLoader(Activity activity, Class<T> clazz, Intent startIntent, String url, String extraKey) {
        super(activity, null, clazz);
        setListener(this);
        mStartIntent = startIntent;
        mUrl = url;
        mExtraKey = extraKey;
    }

    @Override
    public void onFinishLoad(SerializableObject result) {
        if (result != null) {
            mStartIntent.putExtra(mExtraKey, result.loadToJson().toString());
            mContext.startActivity(mStartIntent);
            if (mLoadingFragment != null) {
                mLoadingFragment.loadingFinished();
            }
        }
    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onStarted() {

    }

    @Override
    public String getUrl() {
        return mUrl;
    }

    public void associateWithFragmentAndStartLoading(LoadingFragment fragment) {
        mLoadingFragment = fragment;
        startLoad();
    }
}
