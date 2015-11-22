package com.stuartvancampen.myblog.background;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by Stuart on 18/11/2015.
 */
public class TaskFragment extends Fragment {

    private FragmentAsyncTask<Object> mTask;
    private FragmentAsyncTaskCallbacks mCallbacks;

    /**
     * Hold a reference to the parent Activity so we can report the
     * task's current progress and results. The Android framework
     * will pass us a reference to the newly created Activity after
     * each configuration change.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (FragmentAsyncTaskCallbacks) activity;
        if (mTask != null) {
            mTask.setCallback(mCallbacks);
        }
    }

    /**
     * This method will only be called once when the retained
     * Fragment is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }

    public TaskFragment attachTask(FragmentAsyncTask<Object> task) {
        mTask = task;
        mTask.setCallback(mCallbacks);
        return this;
    }

    public TaskFragment startTask() {
        mTask.execute();
        return this;
    }

    /**
     * Set the callback to null so we don't accidentally leak the
     * Activity instance.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

}
