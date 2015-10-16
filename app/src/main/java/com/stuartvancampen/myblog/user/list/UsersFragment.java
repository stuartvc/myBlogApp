package com.stuartvancampen.myblog.user.list;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stuartvancampen.myblog.R;
import com.stuartvancampen.myblog.user.models.UserPreview;
import com.stuartvancampen.myblog.user.models.UserList;
import com.stuartvancampen.myblog.user.profile.UserProfileActivity;
import com.stuartvancampen.myblog.util.MyAdapter;
import com.stuartvancampen.myblog.util.MyFragment;
import com.stuartvancampen.myblog.util.MyLoader;

/**
 * Created by Stuart on 13/10/2015.
 */
public class UsersFragment extends MyFragment<UserPreview, UserList> {

    public static Fragment create() {
        return new UsersFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user_list_fragment, container, false);

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(R.string.list_of_users);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.user_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public MyLoader createLoader() {
        return new MyLoader<>(mAdapter, UserList.class);
    }

    @Override
    public MyAdapter createAdapter() {
        return new UsersAdapter(this);
    }

    @Override
    public String getUrlPath() {
        return getString(R.string.users_url);
    }

    @Override
    public void OnItemClickListener(View view, int position) {
        startActivity(UserProfileActivity.create(getActivity(), ((UsersAdapter)mAdapter).getItem(position)));
    }
}
