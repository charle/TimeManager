package com.jing.du.Main.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jing.du.Main.Model.Minder;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.MinderViewHolder;
import com.jing.du.common.adapter.MyBaseAdapter;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.utils.DateUtils;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.view.MyBaseHolder;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by charle-chen on 15/7/12.
 */
public class MinderAdapter extends MyBaseAdapter {

    public MinderAdapter(Context context, List<? extends DataSupport> data, MyBaseHolder myBaseHolder, int layoutId) {
        super(context, data, myBaseHolder, layoutId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (!StringUtils.isViewEmpty(convertView)) {
            myBaseHolder = (MinderViewHolder) convertView.getTag();
        } else {
            myBaseHolder = new MinderViewHolder();
            convertView = mInflater.inflate(layoutId, null);
            myBaseHolder.views[0] = (TextView) convertView.findViewById(R.id.tv_minder_title);
            myBaseHolder.views[1] = (TextView) convertView.findViewById(R.id.tv_minder_create_time);
            myBaseHolder.views[2] = (ImageView) convertView.findViewById(R.id.iv_minder_type);
            myBaseHolder.views[3] = (ImageView) convertView.findViewById(R.id.iv_minde_clock);
            myBaseHolder.views[3] = (ImageView) convertView.findViewById(R.id.iv_minde_clock);
            myBaseHolder.views[4] = (TextView) convertView.findViewById(R.id.tv_minder_mind_time);
            convertView.setTag(myBaseHolder);
        }

        Minder minder = (Minder)mData.get(position);
        ((TextView) myBaseHolder.views[0]).setText(minder.getTitle());
        ((TextView) myBaseHolder.views[0]).setTextColor(CommonConstant.MINDER_COLOR[minder.getMinderType()]);
        ((TextView) myBaseHolder.views[1]).setText(DateUtils.getSecondOfDate(minder.getCreatetime()));
        ((TextView) myBaseHolder.views[1]).setTextColor(CommonConstant.MINDER_COLOR[minder.getMinderType()]);
        ((ImageView) myBaseHolder.views[2]).setImageResource(CommonConstant.MINDER_RESOUCE[minder.getMinderType()]);
        ((ImageView) myBaseHolder.views[3]).setImageResource(CommonConstant.RESOUCE_MIND_CLOCK[minder.getMindOrNotMind()]);
        ((TextView) myBaseHolder.views[4]).setText(DateUtils.getSecondOfDate(minder.getMindTime()));
        ((TextView) myBaseHolder.views[4]).setTextColor(CommonConstant.MINDER_COLOR[minder.getMinderType()]);
        return convertView;
    }
}
