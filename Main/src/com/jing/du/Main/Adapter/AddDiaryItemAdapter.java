package com.jing.du.Main.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jing.du.Main.Model.DiaryItem;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.AddDiaryItemViewHolder;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.adapter.MyBaseAdapter;
import com.jing.du.common.view.MyBaseHolder;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by charle-chen on 15/7/1.
 */
public class AddDiaryItemAdapter extends MyBaseAdapter {

    public AddDiaryItemAdapter(Context context, List<? extends DataSupport> data, MyBaseHolder myBaseHolder, int layoutId) {
        super(context, data, myBaseHolder, layoutId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (!StringUtils.isViewEmpty(convertView)) {
            myBaseHolder = (AddDiaryItemViewHolder) convertView.getTag();
        } else {
            myBaseHolder = new AddDiaryItemViewHolder();
            convertView = mInflater.inflate(layoutId, null);
            myBaseHolder.views[0] = (TextView) convertView.findViewById(R.id.category_name);
            myBaseHolder.views[1] = (TextView) convertView.findViewById(R.id.tag_name);
            myBaseHolder.views[2] = (TextView) convertView.findViewById(R.id.tv_begin_time);
            myBaseHolder.views[3] = (TextView) convertView.findViewById(R.id.tv_end_time);
            myBaseHolder.views[4] = (TextView) convertView.findViewById(R.id.tv_note);
            myBaseHolder.views[8] = (ImageView)convertView.findViewById(R.id.iv_tag_icon);
            convertView.setTag(myBaseHolder);
        }
        DiaryItem diaryItem = (DiaryItem) mData.get(position);
        DiaryItem item = DataSupport.find(DiaryItem.class, diaryItem.getId(), true);
        ((TextView) myBaseHolder.views[0]).setText(item.getCategory().getName());
        ((TextView) myBaseHolder.views[1]).setText(item.getTag().getName());
        ((TextView) myBaseHolder.views[2]).setText(item.getBeginTime());
        ((TextView) myBaseHolder.views[3]).setText(item.getEndTime());
        ((TextView) myBaseHolder.views[4]).setText(item.getNote());
        ((ImageView)myBaseHolder.views[8]).setImageResource(CommonConstant.RESOUCE_TAG[item.getTag().getDefaultType()]);
        return convertView;
    }
}
