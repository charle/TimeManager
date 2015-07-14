package com.jing.du.common.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.jing.du.common.utils.StringUtils;

import java.util.List;

/**
 * Created by charle-chen on 15/7/14.
 */
public class MyListEditAdapter extends BaseAdapter {

    protected Context mContext;
    protected List<? extends Object> listData;

    public MyListEditAdapter(Context context, List<? extends Object> listData) {
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

    protected void initEvent(int position) {

    }

    /**
     * 单击事件监听器
     */

    protected onEditItemClickListener editItemClickListener = null;
    protected onDeleteItemClickListener deleteItemClickListener = null;

    public void setOnEditItemClickListener(onEditItemClickListener listener) {
        editItemClickListener = listener;
    }

    public void setOnDeleteItemClickListener(onDeleteItemClickListener listener) {
        deleteItemClickListener = listener;
    }

    public interface onEditItemClickListener {
        void onEditItemClick(View v, int position);
    }

    public interface onDeleteItemClickListener {
        void onDeleteItemClick(View v, int position);
    }

}
