package com.stuartvancampen.myblog.comment;

import android.util.JsonReader;

import com.stuartvancampen.myblog.util.MyList;

/**
 * Created by Stuart on 15/10/2015.
 */
public class CommentList extends MyList<Comment> {
    private static final String JSON_ROOT = "comments";

    @Override
    public Comment createObject(JsonReader reader) {
        return new Comment(reader);
    }

    @Override
    public String getListRoot() {
        return JSON_ROOT;
    }
}
