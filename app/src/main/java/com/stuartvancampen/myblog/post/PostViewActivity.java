package com.stuartvancampen.myblog.post;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.stuartvancampen.myblog.R;
import com.stuartvancampen.myblog.comment.CommentsFragment;
import com.stuartvancampen.myblog.util.MyActivity;

/**
 * Created by Stuart on 16/10/2015.
 */
public class PostViewActivity extends MyActivity {

    private Post mPost;

    public static Intent create(Context context, Post post) {
        Intent startIntent = new Intent(context, PostViewActivity.class);
        Log.d("POST", post.toString());
        startIntent.putExtra("post", post.toString());
        return startIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.header_container);
        View header = getLayoutInflater().inflate(R.layout.post_list_item, null);
        TextView title = (TextView) header.findViewById(R.id.post_title);
        title.setText(mPost.getTitle());
        TextView body = (TextView) header.findViewById(R.id.post_body);
        body.setText(mPost.getBody());
        frameLayout.addView(header);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("post", mPost.toString());
    }

    @Override
    protected void onLoadInstanceState(Bundle savedInstanceState) {
        super.onLoadInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("post")) {
                mPost = new Post(savedInstanceState.getString("post"));
            }
        }
        else {
            Intent intent = getIntent();
            if (intent.hasExtra("post")) {
                mPost = new Post(intent.getStringExtra("post"));
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.my_activity_with_header;
    }

    @Override
    protected Fragment constructFragment() {
        return CommentsFragment.create(mPost);
    }
}
