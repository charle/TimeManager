package com.jing.du.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.jing.du.common.utils.Log;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.view.MyBaseHolder;
import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;

/**
 * Created by charle-chen on 15/6/25.
 */
public class MyBaseAdapter extends BaseAdapter {

    protected LayoutInflater mInflater;
    protected List<? extends DataSupport> mData;
    protected MyBaseHolder myBaseHolder;
    protected int layoutId;
    protected Context mContext;
    protected HashMap<Integer,View> lmap = new HashMap<Integer,View>();

    public MyBaseAdapter(Context context, List<? extends DataSupport> data, MyBaseHolder myBaseHolder, int layoutId) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.myBaseHolder = myBaseHolder;
        this.layoutId = layoutId;
        this.mContext = context;
    }

    @Override
    public long getItemId(int position) {
        Log.d("position:>>>>>>>" + position);
        return position;
    }

    @Override
    public DataSupport getItem(int position) {
        Log.d("position:>>>>>>>" + position);
        if (!StringUtils.isListEmpty(mData))
            return mData.get(position);
        return null;
    }

    @Override
    public int getCount() {
        if (StringUtils.isListEmpty(mData))
            return 0;
        return mData.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }
}
