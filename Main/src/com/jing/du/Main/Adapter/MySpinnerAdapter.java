package com.jing.du.Main.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jing.du.Main.Model.Category;
import com.jing.du.Main.Model.Tag;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.SpinnerViewHolder;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.view.MyBaseAdapter;
import com.jing.du.common.view.MyBaseHolder;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by charle-chen on 15/6/30.
 */
public class MySpinnerAdapter extends MyBaseAdapter {

    private int spinnerId;

    public MySpinnerAdapter(Context context, List<? extends DataSupport> data, MyBaseHolder myBaseHolder, int layoutId) {
        super(context, data, myBaseHolder, layoutId);
    }

    public MySpinnerAdapter(Context context, List<? extends DataSupport> data, MyBaseHolder myBaseHolder, int layoutId, int spinnerId) {
        this(context, data, myBaseHolder, layoutId);
        this.spinnerId = spinnerId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (!StringUtils.isViewEmpty(convertView)) {
            myBaseHolder = (SpinnerViewHolder)convertView.getTag();
        } else {
            myBaseHolder = new SpinnerViewHolder();
            convertView = mInflater.inflate(layoutId, null);
            myBaseHolder.views[0] = (TextView) convertView.findViewById(R.id.tv_spinner);
            convertView.setTag(myBaseHolder);
        }

        String str = "";
        switch (spinnerId) {
            case CommonConstant.SPINNER_ONE_ID:
                Category category = (Category) mData.get(position);
                str = category.getName();
                break;
            case CommonConstant.SPINNER_TWO_ID:
                Tag tag = (Tag) mData.get(position);
                str = tag.getName();
                break;
        }
        ((TextView) myBaseHolder.views[0]).setText(str);
        return convertView;
    }
}
