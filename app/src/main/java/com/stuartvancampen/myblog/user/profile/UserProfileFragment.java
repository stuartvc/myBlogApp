package com.stuartvancampen.myblog.user.profile;

import android.app.Fragment;
import android.content.AsyncTaskLoader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stuartvancampen.myblog.R;
import com.stuartvancampen.myblog.post.PostsActivity;
import com.stuartvancampen.myblog.user.models.User;
import com.stuartvancampen.myblog.user.models.UserPreview;
import com.stuartvancampen.myblog.util.MyAdapter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Stuart on 14/10/2015.
 */
public class UserProfileFragment extends Fragment {

    private static final String TAG = UserProfileFragment.class.getSimpleName();
    private UserPreview mUserPreview;
    private User mUser;

    public static Fragment create(UserPreview userPreview) {
        Fragment frag = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putString("user", userPreview.toString());
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mUserPreview = new UserPreview(args.getString("user"));

        new AsyncTask<UserPreview, String, User>() {

            @Override
            protected User doInBackground(UserPreview... params) {
                try {
                    UserPreview userPreview = params[0];
                    URL url = new URL(userPreview.getFullUserUrl(getActivity()));
                    Log.d(TAG, "loading url:" + url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    // read the response
                    System.out.println("Response Code: " + conn.getResponseCode());
                    InputStreamReader inReader = new InputStreamReader(conn.getInputStream());
                    JsonReader reader = new JsonReader(inReader);

                    reader.beginObject();
                    while (reader.hasNext()) {
                        String name = reader.nextName();
                        if (name == null) {
                            reader.skipValue();
                        }
                        else if (name.equals("user")) {
                            return new User(reader);
                        }
                        else {
                            reader.skipValue();
                        }
                    }
                    reader.endObject();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(User user) {
                onLoadedUser(user);
            }
        }.execute(mUserPreview);
    }

    private void onLoadedUser(User user) {
        mUser = user;
        Log.d(TAG, "loadedUser:" + mUser.toString());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_profile_layout, container, false);

        TextView name = (TextView) view.findViewById(R.id.user_name);
        name.setText(mUserPreview.getName());
        TextView email = (TextView) view.findViewById(R.id.user_email);
        email.setText(mUserPreview.getEmail());

        TextView postsButton = (TextView) view.findViewById(R.id.posts_button);
        postsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(PostsActivity.create(getActivity(), mUser));
            }
        });

        return view;
    }
}
