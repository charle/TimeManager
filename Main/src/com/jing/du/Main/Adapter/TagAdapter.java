package com.jing.du.Main.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.jing.du.Main.Model.Tag;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.TagViewHolder;
import com.jing.du.common.view.InputDialog;
import com.jing.du.common.view.MyBaseAdapter;
import com.jing.du.common.view.MyBaseHolder;
import org.litepal.crud.DataSupport;

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
        myBaseHolder = new TagViewHolder();
        convertView = mInflater.inflate(layoutId, null);
        myBaseHolder.views[0] = (TextView) convertView.findViewById(R.id.tv_name);
        myBaseHolder.views[1] = (BootstrapButton) convertView.findViewById(R.id.bt_edit_tag);
        myBaseHolder.views[2] = (BootstrapButton) convertView.findViewById(R.id.bt_del_tag);
        convertView.setTag(myBaseHolder);

        Tag tag = (Tag) mData.get(position);
        ((TextView) myBaseHolder.views[0]).setText(tag.getName());
        View.OnClickListener btnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.bt_edit_tag:
                        final InputDialog editDialog = new InputDialog(mContext, R.layout.input_dialog, ((Tag) getItem(position)).getId(), InputDialog.EDIT_TAG);
                        editDialog.initView();
                        break;
                    case R.id.bt_del_tag:
                        DataSupport.delete(Tag.class, ((Tag) getItem(position)).getId());
                        notifyDataSetChanged();
                        break;
                }
            }
        };
        myBaseHolder.views[1].setOnClickListener(btnClick);
        myBaseHolder.views[2].setOnClickListener(btnClick);
        return convertView;
    }
}
