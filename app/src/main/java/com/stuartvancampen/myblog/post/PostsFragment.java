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
import com.stuartvancampen.myblog.util.MyAdapter;
import com.stuartvancampen.myblog.util.MyFragment;
import com.stuartvancampen.myblog.util.MyLoader;

/**
 * Created by Stuart on 21/11/2015.
 */
public class PostsFragment extends MyFragment<Post, PostList> {

    private static final String TAG = PostsFragment.class.getSimpleName();

    public static Fragment create() {
        return new PostsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_list_fragment, container, false);

        TextView userName = (TextView) view.findViewById(R.id.user_name);
        userName.setText("Recent Posts");

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.post_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public MyLoader createLoader() {
        return new MyLoader<>(getActivity(), mAdapter, PostList.class);
    }

    @Override
    public MyAdapter createAdapter() {
        return new PostsAdapter(this);
    }

    @Override
    public String getUrlPath() {
        return getString(R.string.posts_url);
    }

    @Override
    public void OnItemClickListener(View view, int position) {
        Log.d(TAG, "onClick, position:" + position);
        getActivity().startActivity(PostViewActivity.create(getActivity(), ((PostsAdapter)mAdapter).getItem(position)));
    }
}
