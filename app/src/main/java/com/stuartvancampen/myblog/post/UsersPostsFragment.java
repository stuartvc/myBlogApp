package com.stuartvancampen.myblog.post;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stuartvancampen.myblog.R;
import com.stuartvancampen.myblog.user.models.User;
import com.stuartvancampen.myblog.util.MyAdapter;
import com.stuartvancampen.myblog.util.MyFragment;
import com.stuartvancampen.myblog.util.MyLoader;

/**
 * Created by Stuart on 15/10/2015.
 */
public class UsersPostsFragment extends PostsFragment {

    private static final String TAG = UsersPostsFragment.class.getSimpleName();
    private User mUser;

    public static Fragment create(User user) {
        Fragment frag = new UsersPostsFragment();
        Bundle args = new Bundle();
        args.putString("user", user.toString());
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mUser = new User(args.getString("user"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (view == null) {
            view = inflater.inflate(R.layout.post_list_fragment, container, false);
        }

        TextView userName = (TextView) view.findViewById(R.id.user_name);
        userName.setText(mUser.getName());

        return view;
    }

    @Override
    public String getUrlPath() {
        return getString(R.string.users_url) + String.valueOf(mUser.getId()) + "/" + getString(R.string.posts_url);
    }
}
