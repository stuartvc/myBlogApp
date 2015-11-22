package com.stuartvancampen.myblog.post;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.stuartvancampen.myblog.R;
import com.stuartvancampen.myblog.background.AsyncJsonHTTPTask;
import com.stuartvancampen.myblog.util.MyActivity;

/**
 * Created by Stuart on 19/11/2015.
 */
public class NewPostDialog extends DialogFragment {

    private static final String TAG = NewPostDialog.class.getSimpleName();
    private EditText mTitle;
    private EditText mBody;

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, TAG);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialog = inflater.inflate(R.layout.new_post, null, false);

        mTitle = (EditText) dialog.findViewById(R.id.post_title);
        mBody = (EditText) dialog.findViewById(R.id.post_body);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.create_post_button)
                .setView(dialog)
                .setPositiveButton(R.string.post, mOnPostListener)
                .setNegativeButton(R.string.cancel, mOnCancelListener)
                .show();
    }

    DialogInterface.OnClickListener mOnPostListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Post newPost = new Post(mTitle.getText().toString(), mBody.getText().toString());
            ((MyActivity) getActivity()).startAsyncTask(new AsyncJsonHTTPTask<>(Post.class, newPost, Post.getCreateUrl(getActivity()), AsyncJsonHTTPTask.HttpVerb.POST));
            dismiss();
        }
    };

    DialogInterface.OnClickListener mOnCancelListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dismiss();
        }
    };
}
