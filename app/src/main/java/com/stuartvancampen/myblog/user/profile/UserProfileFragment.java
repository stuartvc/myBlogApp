package com.stuartvancampen.myblog.user.profile;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stuartvancampen.myblog.R;
import com.stuartvancampen.myblog.post.NewPostDialog;
import com.stuartvancampen.myblog.post.UsersPostsActivity;
import com.stuartvancampen.myblog.user.models.User;
import com.stuartvancampen.myblog.util.BaseFragment;

/**
 * Created by Stuart on 14/10/2015.
 */
public class UserProfileFragment extends BaseFragment{

    private static final String TAG = UserProfileFragment.class.getSimpleName();

    private static final String EXTRA_USER = "user";

    private User mUser;

    public static Fragment create(User user) {
        Fragment frag = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_USER, user.toString());
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(EXTRA_USER)) {
                mUser = new User(args.getString(EXTRA_USER));
            }
            else {
                Log.e(TAG, "user object not found in arguments, finishing");
                getActivity().finish();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_profile_layout, container, false);

        TextView name = (TextView) view.findViewById(R.id.user_name);
        name.setText(mUser.getName());
        TextView email = (TextView) view.findViewById(R.id.user_email);
        email.setText(mUser.getEmail());

        TextView postsButton = (TextView) view.findViewById(R.id.posts_button);
        postsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(UsersPostsActivity.create(getActivity(), mUser));
            }
        });

        TextView createPostButton = (TextView) view.findViewById(R.id.create_post_button);
        createPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewPostDialog();
            }
        });

        return view;
    }

    private void showNewPostDialog() {
        new NewPostDialog().show(getFragmentManager());
    }
}
