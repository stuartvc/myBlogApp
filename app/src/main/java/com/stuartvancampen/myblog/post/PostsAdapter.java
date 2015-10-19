package com.stuartvancampen.myblog.post;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stuartvancampen.myblog.R;
import com.stuartvancampen.myblog.util.MyAdapter;
import com.stuartvancampen.myblog.util.MyFragment;

/**
 * Created by Stuart on 15/10/2015.
 */
public class PostsAdapter extends MyAdapter<PostVH, Post, PostList> {

    private static final String TAG = PostsAdapter.class.getSimpleName();
    
    public PostsAdapter(MyFragment frag) {
        super(frag);
    }

    @Override
    public PostVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent, false);

        PostVH PostVH = new PostVH(v);
        PostVH.setOnItemClickListener(mFragment);
        return PostVH;
    }

    @Override
    public void onBindViewHolder(PostVH holder, int position) {
        Post post = getItem(position);
        if (post != null) {
            holder.mTitle.setText(post.getTitle());
            holder.mBody.setText(post.getBody());
        }
        else {
            Log.e(TAG, "could not find post at position " + String.valueOf(position));
        }
    }
}
