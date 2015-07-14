package com.jing.du.Main.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.jing.du.Main.Activity.MainActivity;
import com.jing.du.Main.Model.Tag;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.TagViewHolder;
import com.jing.du.common.adapter.MyBaseAdapter;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.view.InputDialog;
import com.jing.du.common.view.MyBaseHolder;
import org.litepal.crud.DataSupport;
import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by charle-chen on 15/6/26.
 */
public class TagAdapter extends MyBaseAdapter {

    public TagAdapter(Context context, List<? extends DataSupport> data, MyBaseHolder myBaseHolder, int layoutId) {
        super(context, data, myBaseHolder, layoutId);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (!StringUtils.isViewEmpty(convertView)) {
            myBaseHolder = (TagViewHolder) convertView.getTag();
        } else {
            myBaseHolder = new TagViewHolder();
            convertView = mInflater.inflate(layoutId, null);
            myBaseHolder.views[0] = (TextView) convertView.findViewById(R.id.tv_name);
            myBaseHolder.views[1] = (ImageView) convertView.findViewById(R.id.iv_tag_icon);
            convertView.setTag(myBaseHolder);
        }

        Tag tag = (Tag) mData.get(position);
        ((TextView)myBaseHolder.views[0]).setText(tag.getName());
        ((ImageView)myBaseHolder.views[1]).setImageResource(CommonConstant.RESOUCE_TAG[tag.getDefaultType()]);
        return convertView;
    }
}
