package com.stuartvancampen.myblog.background;

/**
 * Created by Stuart on 18/11/2015.
 */
public interface FragmentAsyncTaskCallbacks<T> {

    void onPreExecute();
    void onProgressUpdate(int percent);
    void onCancelled();
    void onPostExecute(T result);
}
