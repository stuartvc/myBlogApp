package com.stuartvancampen.myblog.background;

import android.os.AsyncTask;

/**
 * Created by Stuart on 18/11/2015.
 */
public abstract class FragmentAsyncTask<Result> extends AsyncTask<Void, Void, Result> {


    private FragmentAsyncTaskCallbacks<Result> mCallback;

    public FragmentAsyncTask() {
    }

    public void setCallback(FragmentAsyncTaskCallbacks callback) {
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        if (mCallback != null) {
            mCallback.onPreExecute();
        }
    }

    @Override
    protected void onCancelled() {
        if (mCallback != null) {
            mCallback.onCancelled();
        }
    }

    @Override
    protected void onPostExecute(Result result) {
        if (mCallback != null) {
            mCallback.onPostExecute(result);
        }
    }

}
