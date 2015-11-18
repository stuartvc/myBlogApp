package com.stuartvancampen.myblog.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.stuartvancampen.myblog.user.models.User;

/**
 * Created by Stuart on 15/11/2015.
 *
 *  This class holds the session data for the user
 */
public class Session {

    public static final String SESSION_PREFERENCES = "user";

    private static final String KEY_USER = "user";
    private static final String KEY_IS_USER_LOGGED_IN = "is_user_logged_in";

    private final SharedPreferences mSettings;
    private User mUser;
    private boolean mIsLoggedIn;
    private static Session mUserSession;

    public static Session getInstance() {
        if (mUserSession == null) {
            mUserSession = new Session();
        }
        return mUserSession;
    }

    private Session() {
        Context context = MyBlogApplication.getInstance();
        mSettings = context.getSharedPreferences(SESSION_PREFERENCES, Context.MODE_PRIVATE);
        mIsLoggedIn = mSettings.getBoolean(KEY_IS_USER_LOGGED_IN, false);
        if (isLoggedIn()) {
            String userString = mSettings.getString(KEY_USER, null);
            if (userString != null) {
                mUser = new User(userString);
            }
        }
    }

    public void doLogin(User user) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(KEY_USER, user.toString());
        editor.putBoolean(KEY_IS_USER_LOGGED_IN, true);
        editor.apply();
        mIsLoggedIn = true;
        mUser = user;
    }

    public void doLogout() {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.remove(KEY_USER);
        editor.remove(KEY_IS_USER_LOGGED_IN);
        editor.apply();
        mIsLoggedIn = false;
        mUser = null;
    }

    public void updateUser(User newUser) {
        doLogin(newUser);
    }

    @SuppressWarnings("unused")
    public User getLoggedInUser() {
        return mUser;
    }

    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }

    public boolean isLoggedInUser(User user) {
        return (mUser != null && mUser.equals(user));
    }
}
