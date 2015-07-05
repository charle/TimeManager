package com.jing.du.common.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.Map;

/**
 * Created by charle-chen on 15/7/5.
 */
public class MyBaseExpandListAdapter extends BaseExpandableListAdapter {

    public Map<String,List<? extends DataSupport>> map = null;
    public List<? extends DataSupport> parent = null;
    public Context mContext;


    public MyBaseExpandListAdapter(Context mContex, Map<String, List<? extends DataSupport>> root) {
        this.mContext = mContex;
        this.map = root;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        DataSupport key = parent.get(groupPosition);
        return (map.get(key).get(childPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        initChildView(convertView);
        initChildData(childPosition,groupPosition);
        return convertView;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parent.get(groupPosition);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        initGroupView(convertView);
        initGroupData(groupPosition);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getGroupCount() {
        return parent.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        DataSupport key = parent.get(groupPosition);
        int size = map.get(key).size();
        return size;
    }

    protected void initChildView(View view){};
    protected void initChildData(int childPosition,int groupPosition){};
    protected void initGroupView(View view){};
    protected void initGroupData(int groupPosition){};
}
