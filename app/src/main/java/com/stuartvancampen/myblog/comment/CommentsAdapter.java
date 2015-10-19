package com.stuartvancampen.myblog.comment;

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
public class CommentsAdapter extends MyAdapter<CommentVH, Comment, CommentList> {

    private static final String TAG = CommentsAdapter.class.getSimpleName();

    public CommentsAdapter(MyFragment frag) {
        super(frag);
    }

    @Override
    public CommentVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent, false);

        CommentVH commentVH = new CommentVH(v);
        commentVH.setOnItemClickListener(mFragment);
        return commentVH;
    }

    @Override
    public void onBindViewHolder(CommentVH holder, int position) {
        Comment comment = getItem(position);
        if (comment != null) {
            holder.mBody.setText(comment.getBody());
        }
        else {
            Log.e(TAG, "could not find user at position " + String.valueOf(position));
        }
    }
}
