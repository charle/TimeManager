package com.jing.du.Main.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jing.du.Main.Model.Diary;
import com.jing.du.Main.Model.DiaryItem;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.DiaryViewHolder;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.utils.DateUtils;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.adapter.MyBaseAdapter;
import com.jing.du.common.view.MyBaseHolder;
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
            myBaseHolder.views[2] = (TextView) convertView.findViewById(R.id.tv_diary_item1);
            myBaseHolder.views[3] = (TextView) convertView.findViewById(R.id.tv_diary_item2);
            myBaseHolder.views[4] = (TextView) convertView.findViewById(R.id.tv_diary_item3);
            myBaseHolder.views[5] = (ImageView) convertView.findViewById(R.id.iv_weather);
            convertView.setTag(myBaseHolder);
        }

        Diary tempDairy = (Diary) mData.get(position);
        ((TextView) myBaseHolder.views[0]).setText(DateUtils.getDateTian(tempDairy.getCreateTime()));
        ((TextView) myBaseHolder.views[1]).setText(DateUtils.getDateMonth(tempDairy.getCreateTime()));
        ((ImageView) myBaseHolder.views[5]).setImageResource(CommonConstant.WEATHER_RESOUCE[tempDairy.getWeatherType()]);
        List<? extends DataSupport> diaryItems = ((Diary) mData.get(position)).getDiaryItemArrayList();
        if(diaryItems.size()==1){
            ((TextView) myBaseHolder.views[2]).setText(getAllDiaryItemInfo((DiaryItem)diaryItems.get(0)));
            ((TextView) myBaseHolder.views[3]).setText("");
            ((TextView) myBaseHolder.views[4]).setText("");
        }else if(diaryItems.size()==2){
            ((TextView) myBaseHolder.views[2]).setText(getAllDiaryItemInfo((DiaryItem)diaryItems.get(0)));
            ((TextView) myBaseHolder.views[3]).setText(getAllDiaryItemInfo((DiaryItem)diaryItems.get(1)));
            ((TextView) myBaseHolder.views[4]).setText("");
        }else if (diaryItems.size()>=3){
            ((TextView) myBaseHolder.views[2]).setText(getAllDiaryItemInfo((DiaryItem)diaryItems.get(0)));
            ((TextView) myBaseHolder.views[3]).setText(getAllDiaryItemInfo((DiaryItem)diaryItems.get(1)));
            ((TextView) myBaseHolder.views[4]).setText(getAllDiaryItemInfo((DiaryItem)diaryItems.get(2)));
        }
        return convertView;
    }

    private String getAllDiaryItemInfo(DiaryItem diaryItem){
        String str = "";
        DiaryItem item = DataSupport.find(DiaryItem.class,diaryItem.getId(),true);
        str=item.getCategory().getName()+":"+item.getTag().getName()+" "+item.getBeginTime()+"~"+item.getEndTime();
        return str;
    }
}
