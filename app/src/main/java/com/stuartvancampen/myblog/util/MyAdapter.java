package com.stuartvancampen.myblog.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import com.stuartvancampen.myblog.R;

/**
 * Created by Stuart on 08/10/2015.
 */
public abstract class MyAdapter<VH extends RecyclerView.ViewHolder, T extends MyObject, LT extends MyList<T>>
        extends RecyclerView.Adapter<VH>
        implements MyLoader.MyLoaderCallbacks<LT> {

    protected final MyFragment mFragment;
    private LT mList;

    public MyAdapter(MyFragment frag) {
        mFragment = frag;
        mList = null;
    }

    protected LayoutInflater getInflater() {
        return (LayoutInflater)mFragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onFinishLoad(LT result) {
        if (result != null) {
            if (mList == null) {
                mList = result;
            } else {
                mList.addAll(result);
            }
            notifyDataSetChanged();
            mFragment.onDataLoaded();
            Log.d("MyAdapter", "onFinishLoad:");
            for (int index = 0; index < result.size(); index++) {
                Log.d("MyAdapter", "\nitem " + index + ": " + result.get(index).loadToJson().toString());
            }
        }
        else {
            Log.d("MyAdapter", "result empty");
        }
    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onStarted() {

    }

    @Override
    public String getUrl() {
        return mFragment.getString(R.string.base_url) + mFragment.getUrlPath();
    }

    public T getItem(int position) {
        return (mList == null) ? null : mList.get(position);
    }

    @Override
    public int getItemCount() {
        return (mList == null) ? 0 : mList.size();
    }
}
