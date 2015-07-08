package com.jing.du.common.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.jing.du.common.utils.StringUtils;

import java.util.List;

/**
 * Created by charle-chen on 15/7/8.
 */
public class SwipeListBaseAdapter extends BaseAdapter {

    protected Context mContext;
    protected List<? extends Object> listData;

    public SwipeListBaseAdapter(Context context, List<? extends Object> listData) {
        this.listData = listData;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return StringUtils.isListEmpty(listData) ? 0 : listData.size();
    }

    @Override
    public Object getItem(int position) {
        return StringUtils.isListEmpty(listData) ? null : listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = initView(convertView);
        initData(position);
        initEvent(position);
        return convertView;
    }

    protected View initView(View converView) {
        return converView;
    }

    protected void initData(int position) {

    }

    protected void initEvent(int position){

    }

    /**
     * 单击事件监听器
     */
    protected onRightItemClickListener mListener = null;

    public void setOnRightItemClickListener(onRightItemClickListener listener){
    	mListener = listener;
    }

    public interface onRightItemClickListener {
        void onRightItemClick(View v, int position);
    }
}


