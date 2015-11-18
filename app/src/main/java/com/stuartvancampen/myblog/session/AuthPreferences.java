package com.stuartvancampen.myblog.session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Stuart on 17/11/2015.
 *
 * This class handles auth token for API requests
 * use AuthPreferences.get() to retrieve AuthPreferences singleton
 */
public class AuthPreferences {

    public static final String AUTH_PREFERENCES = "auth";

    public static final String KEY_AUTH_TOKEN = "auth_token";

    private static AuthPreferences sInstance;
    private final SharedPreferences mPrefs;

    public static AuthPreferences get() {
        if (sInstance == null) {
            sInstance = new AuthPreferences();
        }
        return  sInstance;
    }

    private AuthPreferences() {
        mPrefs = MyBlogApplication.getInstance().getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
    }

    public String getAuthToken() {
        return mPrefs.getString(KEY_AUTH_TOKEN, null);
    }

    public void setAuthToken(String token) {
        mPrefs.edit().putString(KEY_AUTH_TOKEN, token).apply();
    }

    public void clearAuthToken() {
        mPrefs.edit().remove(KEY_AUTH_TOKEN).apply();
        Session.getInstance().doLogout();
    }


}
