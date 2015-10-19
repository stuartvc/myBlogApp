package com.stuartvancampen.myblog.user.list;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stuartvancampen.myblog.R;
import com.stuartvancampen.myblog.user.models.UserPreview;
import com.stuartvancampen.myblog.user.models.UserList;
import com.stuartvancampen.myblog.util.MyAdapter;
import com.stuartvancampen.myblog.util.MyFragment;

/**
 * Created by Stuart on 13/10/2015.
 */
public class UsersAdapter extends MyAdapter<UserVH, UserPreview, UserList> {
    private static final String TAG = UsersAdapter.class.getSimpleName();

    public UsersAdapter(MyFragment frag) {
        super(frag);
    }

    @Override
    public UserVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);

        UserVH userVH = new UserVH(v);
        userVH.setOnItemClickListener(mFragment);
        return userVH;
    }

    @Override
    public void onBindViewHolder(UserVH holder, int position) {
        UserPreview userPreview = getItem(position);
        if (userPreview != null) {
            holder.mName.setText(userPreview.getName());
            holder.mEmail.setText(userPreview.getEmail());
        }
        else {
            Log.e(TAG, "could not find user at position " + String.valueOf(position));
        }
    }

}
