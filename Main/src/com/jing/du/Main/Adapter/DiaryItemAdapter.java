package com.jing.du.Main.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jing.du.Main.Model.DiaryItem;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.DiaryItemViewHolder;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.adapter.MyBaseAdapter;
import com.jing.du.common.view.MyBaseHolder;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by charle-chen on 15/6/29.
 */
public class DiaryItemAdapter extends MyBaseAdapter {

    public DiaryItemAdapter(Context context, List<? extends DataSupport> data, MyBaseHolder myBaseHolder, int layoutId) {
        super(context, data, myBaseHolder, layoutId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (!StringUtils.isViewEmpty(convertView)) {
            myBaseHolder = (DiaryItemViewHolder) convertView.getTag();
        } else {
            myBaseHolder = new DiaryItemViewHolder();
            convertView = mInflater.inflate(layoutId, null);
            myBaseHolder.views[0] = (TextView) convertView.findViewById(R.id.tv_tagname);
            myBaseHolder.views[1] = (TextView) convertView.findViewById(R.id.tv_begintime);
            myBaseHolder.views[2] = (TextView) convertView.findViewById(R.id.tv_endtime);
            convertView.setTag(myBaseHolder);
        }
        DiaryItem diaryItem = (DiaryItem) mData.get(position);
        DiaryItem item = DataSupport.find(DiaryItem.class, diaryItem.getId(), true);
        ((TextView) myBaseHolder.views[0]).setText(item.getCategory().getName() + ":" + item.getTag().getName());
        ((TextView) myBaseHolder.views[1]).setText(diaryItem.getBeginTime());
        ((TextView) myBaseHolder.views[2]).setText(diaryItem.getEndTime());
        return convertView;
    }
}
