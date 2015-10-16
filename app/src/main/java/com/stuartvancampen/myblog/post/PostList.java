package com.stuartvancampen.myblog.post;

import android.util.JsonReader;

import com.stuartvancampen.myblog.util.MyList;

/**
 * Created by Stuart on 15/10/2015.
 */
public class PostList extends MyList<Post> {
    private static final String JSON_ROOT = "posts";

    @Override
    public Post createObject(JsonReader reader) {
        return new Post(reader);
    }

    @Override
    public String getListRoot() {
        return JSON_ROOT;
    }
}
