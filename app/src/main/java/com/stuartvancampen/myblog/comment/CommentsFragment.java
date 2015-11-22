package com.stuartvancampen.myblog.comment;

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
import com.stuartvancampen.myblog.post.Post;
import com.stuartvancampen.myblog.util.MyAdapter;
import com.stuartvancampen.myblog.util.MyFragment;
import com.stuartvancampen.myblog.util.MyLoader;

/**
 * Created by Stuart on 16/10/2015.
 */
public class CommentsFragment extends MyFragment<Comment, CommentList> {

    private static final String TAG = CommentsFragment.class.getSimpleName();
    private Post mPost;

    public static Fragment create(Post post) {
        Fragment frag = new CommentsFragment();
        Bundle args = new Bundle();
        args.putString("post", post.toString());
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mPost = new Post(args.getString("post"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comment_list_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.comment_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);

        TextView newCommentButton = (TextView) view.findViewById(R.id.new_comment_button);
        newCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NewCommentDialog().show(getFragmentManager(), mPost);
            }
        });

        return view;
    }

    @Override
    public MyLoader createLoader() {
        return new MyLoader<>(getActivity(), mAdapter, CommentList.class);
    }

    @Override
    public MyAdapter createAdapter() {
        return new CommentsAdapter(this);
    }

    @Override
    public String getUrlPath() {
        return getString(R.string.posts_url) + String.valueOf(mPost.getId()) + "/" + getString(R.string.comments_url);
    }

    @Override
    public void OnItemClickListener(View view, int position) {

    }
}
