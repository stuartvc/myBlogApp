package com.stuartvancampen.myblog.util;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Stuart on 14/11/2015.
 */
public class RemoteJsonObjectLoader<T extends SerializableObject> extends MyLoader<T> implements MyLoader.MyLoaderCallbacks<T> {

    private final Intent mStartIntent;
    private final String mUrl;
    private final String mExtraKey;

    public RemoteJsonObjectLoader(Context context, Class<T> clazz, Intent startIntent, String url, String extraKey) {
        super(context, null, clazz);
        setListener(this);
        mStartIntent = startIntent;
        mUrl = url;
        mExtraKey = extraKey;
        startLoad();
    }

    @Override
    public void onFinishLoad(SerializableObject result) {
        mStartIntent.putExtra(mExtraKey, result.loadToJson().toString());
        mContext.startActivity(mStartIntent);
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
}
