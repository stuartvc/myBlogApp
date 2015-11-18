package com.stuartvancampen.myblog.session;

import android.app.Application;

/**
 * Created by Stuart on 15/11/2015.
 *
 * Overrides the Application class and provides static access to Context
 */
public class MyBlogApplication extends Application {

    private static MyBlogApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
    }

    public static MyBlogApplication getInstance() {
        return sInstance;
    }
}
