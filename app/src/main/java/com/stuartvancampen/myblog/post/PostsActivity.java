package com.stuartvancampen.myblog.post;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.stuartvancampen.myblog.user.models.User;
import com.stuartvancampen.myblog.util.MyActivity;

/**
 * Created by Stuart on 21/11/2015.
 */
public class PostsActivity extends MyActivity{

    public static Intent create(Context context) {
        return new Intent(context, PostsActivity.class);
    }

    @Override
    protected Fragment constructFragment() {
        return PostsFragment.create();
    }
}
