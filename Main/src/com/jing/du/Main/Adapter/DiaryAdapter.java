package com.jing.du.Main.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.jing.du.Main.Model.Diary;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.DiaryViewHolder;
import com.jing.du.Main.ViewHolder.HomeTagViewHolder;
import com.jing.du.common.utils.DateUtils;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.view.MyBaseAdapter;
import com.jing.du.common.view.MyBaseHolder;
import com.jing.du.common.view.MyInnerListView;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by charle-chen on 15/6/27.
 */
public class DiaryAdapter extends MyBaseAdapter {

    public DiaryAdapter(Context context, List<? extends DataSupport> data, MyBaseHolder myBaseHolder, int layoutId) {
        super(context, data, myBaseHolder, layoutId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (!StringUtils.isViewEmpty(convertView)) {
            myBaseHolder = (DiaryViewHolder) convertView.getTag();
        } else {
            myBaseHolder = new DiaryViewHolder();
            convertView = mInflater.inflate(layoutId, null);
            myBaseHolder.views[0] = (TextView) convertView.findViewById(R.id.tv_date);
            myBaseHolder.views[1] = (TextView) convertView.findViewById(R.id.tv_month);
            myBaseHolder.views[2] = (MyInnerListView) convertView.findViewById(R.id.lv_taglist);
            myBaseHolder.views[3] = (ImageView) convertView.findViewById(R.id.iv_category);
            convertView.setTag(myBaseHolder);
        }

        Diary tempDairy = (Diary) mData.get(position);
        ((TextView) myBaseHolder.views[0]).setText(DateUtils.getDateTian(tempDairy.getCreateTime()));
        ((TextView) myBaseHolder.views[1]).setText(DateUtils.getDateMonth(tempDairy.getCreateTime()));
        ((ImageView) myBaseHolder.views[3]).setImageResource(R.drawable.app_icon);
        List<? extends DataSupport> diaryItems = ((Diary) mData.get(position)).getDiaryItemArrayList();
        if (!StringUtils.isListEmpty(diaryItems)) {
            HomeTagViewHolder tagViewHolder = new HomeTagViewHolder();
            DiaryItemAdapter diaryItemAdapter = new DiaryItemAdapter(mContext, diaryItems, tagViewHolder, R.layout.home_list_main_tag_item);
            ((ListView) myBaseHolder.views[2]).setAdapter(diaryItemAdapter);
        }

        return convertView;
    }
}
