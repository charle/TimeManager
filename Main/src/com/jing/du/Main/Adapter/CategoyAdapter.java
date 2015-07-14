package com.jing.du.Main.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.jing.du.Main.Activity.DetailCategoryActivity;
import com.jing.du.Main.Model.Category;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.CategoyViewHolder;
import com.jing.du.common.adapter.MyBaseAdapter;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.view.MyBaseHolder;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by charle-chen on 15/6/25.
 */
public class CategoyAdapter extends MyBaseAdapter {

    public CategoyAdapter(Context context, List<? extends DataSupport> data, MyBaseHolder myBaseHolder, int layoutId) {
        super(context, data, myBaseHolder, layoutId);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (!StringUtils.isViewEmpty(convertView)) {
            myBaseHolder = (CategoyViewHolder) convertView.getTag();
        } else {
            myBaseHolder = new CategoyViewHolder();
            convertView = mInflater.inflate(layoutId, null);
            myBaseHolder.views[0] = (ImageView) convertView.findViewById(R.id.iv_category_icon);
            myBaseHolder.views[1] = (TextView) convertView.findViewById(R.id.tv_category_name);
            convertView.setTag(myBaseHolder);
        }

        Category category = (Category) mData.get(position);
        ((ImageView) myBaseHolder.views[0]).setImageResource(CommonConstant.RESOUCE_CATEGORY[category.getDefaultType()]);
        ((TextView) myBaseHolder.views[1]).setText(category.getName());
        return convertView;
    }
}
