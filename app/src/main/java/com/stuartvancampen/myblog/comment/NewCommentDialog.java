package com.stuartvancampen.myblog.comment;

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
import com.stuartvancampen.myblog.post.Post;
import com.stuartvancampen.myblog.util.MyActivity;

/**
 * Created by Stuart on 21/11/2015.
 */
public class NewCommentDialog extends DialogFragment{

    private static final String TAG = NewCommentDialog.class.getSimpleName();
    private EditText mBody;
    private Post mPost;

    public void show(FragmentManager fragmentManager, Post post) {
        show(fragmentManager, TAG);
        mPost = post;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialog = inflater.inflate(R.layout.new_comment, null, false);

        mBody = (EditText) dialog.findViewById(R.id.comment_body);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.create_comment_button)
                .setView(dialog)
                .setPositiveButton(R.string.comment, mOnPostListener)
                .setNegativeButton(R.string.cancel, mOnCancelListener)
                .show();
    }

    DialogInterface.OnClickListener mOnPostListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Comment newComment = new Comment(mBody.getText().toString(), "");
            ((MyActivity) getActivity()).startAsyncTask(new AsyncJsonHTTPTask<>(Comment.class, newComment, Comment.getCreateUrl(getActivity(), mPost), AsyncJsonHTTPTask.HttpVerb.POST));
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
