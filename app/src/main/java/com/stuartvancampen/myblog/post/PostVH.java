package com.stuartvancampen.myblog.post;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.stuartvancampen.myblog.R;
import com.stuartvancampen.myblog.util.OnItemClickListener;

/**
 * Created by Stuart on 15/10/2015.
 */
public class PostVH extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = PostVH.class.getSimpleName();
    public TextView mTitle;
    public TextView mBody;
    public TextView mUser;
    private OnItemClickListener mOnItemClickListener;


    public PostVH(View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.post_title);
        mBody = (TextView) itemView.findViewById(R.id.post_body);
        mUser = (TextView) itemView.findViewById(R.id.post_creator);
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
