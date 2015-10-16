package com.stuartvancampen.myblog.comment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.stuartvancampen.myblog.R;
import com.stuartvancampen.myblog.util.OnItemClickListener;

/**
 * Created by Stuart on 15/10/2015.
 */
public class CommentVH extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = CommentVH.class.getSimpleName();
    public TextView mBody;
    private OnItemClickListener mOnItemClickListener;

    public CommentVH(View itemView) {
        super(itemView);
        mBody = (TextView) itemView.findViewById(R.id.comment_body);
        itemView.setOnClickListener(this);
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "clicked " + getAdapterPosition());
        mOnItemClickListener.OnItemClickListener(v, getAdapterPosition());
    }
}
