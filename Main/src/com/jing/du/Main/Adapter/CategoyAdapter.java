package com.jing.du.Main.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.jing.du.Main.Model.Category;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.CategoyViewHolder;
import com.jing.du.Main.ViewHolder.TagViewHolder;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.view.InputDialog;
import com.jing.du.common.view.MyBaseAdapter;
import com.jing.du.common.view.MyBaseHolder;
import com.jing.du.common.view.MyInnerListView;
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
            myBaseHolder.views[0] = (TextView) convertView.findViewById(R.id.tv_name);
            myBaseHolder.views[1] = (BootstrapButton) convertView.findViewById(R.id.bt_add_tag);
            myBaseHolder.views[2] = (BootstrapButton) convertView.findViewById(R.id.bt_del_category);
            myBaseHolder.views[3] = (BootstrapButton) convertView.findViewById(R.id.bt_edit_category);
            myBaseHolder.views[4] = (MyInnerListView) convertView.findViewById(R.id.item_tag);
            convertView.setTag(myBaseHolder);
        }

        Category tempCategory = (Category) mData.get(position);
        ((TextView) myBaseHolder.views[0]).setText(tempCategory.getName());

        List<? extends DataSupport> tags = ((Category) mData.get(position)).getTagList();
        TagViewHolder tagViewHolder = new TagViewHolder();
        final TagAdapter tagAdapter = new TagAdapter(mContext, tags, tagViewHolder, R.layout.tag_item);
        ((ListView) myBaseHolder.views[4]).setAdapter(tagAdapter);

        View.OnClickListener btnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.bt_add_tag:
                        final InputDialog addDialog = new InputDialog(mContext, R.layout.input_dialog, ((Category) getItem(position)).getId(), InputDialog.ADD_TAG);
                        addDialog.initView();
                        break;
                    case R.id.bt_edit_category:
                        final InputDialog editDialog = new InputDialog(mContext, R.layout.input_dialog, ((Category) getItem(position)).getId(), InputDialog.EDIT_CATEGORY);
                        editDialog.initView();
                        break;
                    case R.id.bt_del_category:
                        DataSupport.delete(Category.class, ((Category) getItem(position)).getId());
                        break;
                }
            }
        };
        myBaseHolder.views[1].setOnClickListener(btnClick);
        myBaseHolder.views[2].setOnClickListener(btnClick);
        myBaseHolder.views[3].setOnClickListener(btnClick);

        return convertView;
    }
}
