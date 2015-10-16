package com.stuartvancampen.myblog.user.list;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.stuartvancampen.myblog.R;
import com.stuartvancampen.myblog.util.OnItemClickListener;

/**
 * Created by Stuart on 14/10/2015.
 */
public class UserVH extends RecyclerView.ViewHolder implements View.OnClickListener{

    private static final String TAG = UserVH.class.getSimpleName();
    public TextView mName;
    public TextView mEmail;
    private OnItemClickListener mOnItemClickListener;

    public UserVH(View itemView) {
        super(itemView);
        mName = (TextView) itemView.findViewById(R.id.user_name);
        mEmail = (TextView) itemView.findViewById(R.id.user_email);
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
