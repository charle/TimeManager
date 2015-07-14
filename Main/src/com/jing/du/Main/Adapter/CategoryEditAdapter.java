package com.jing.du.Main.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.jing.du.Main.Model.Tag;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.CategoryEditViewHolder;
import com.jing.du.common.adapter.MyListEditAdapter;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.utils.StringUtils;

import java.util.List;

/**
 * Created by charle-chen on 15/7/14.
 */
public class CategoryEditAdapter extends MyListEditAdapter {

    private CategoryEditViewHolder addDiaryItemViewHolder;
    private List<Tag> list;

    public CategoryEditAdapter(Context context, List<? extends Object> listData) {
        super(context, listData);
        this.list = (List<Tag>) listData;
    }

    @Override
    protected View initView(View converView) {
        if (!StringUtils.isViewEmpty(converView)) {
            addDiaryItemViewHolder = (CategoryEditViewHolder) converView.getTag();
        } else {
            addDiaryItemViewHolder = new CategoryEditViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            converView = mInflater.inflate(R.layout.edit_category_item, null);
            addDiaryItemViewHolder.views[0] = (ImageView) converView.findViewById(R.id.iv_tag_icon);
            addDiaryItemViewHolder.views[1] = (EditText) converView.findViewById(R.id.et_tag_name);
            addDiaryItemViewHolder.views[2] = (BootstrapButton) converView.findViewById(R.id.bt_edit_tag);
            addDiaryItemViewHolder.views[3] = (BootstrapButton) converView.findViewById(R.id.bt_delete_tag);
            converView.setTag(addDiaryItemViewHolder);
        }
        return converView;
    }

    @Override
    protected void initData(int position) {
        super.initData(position);
        Tag tag = list.get(position);
        ((ImageView) addDiaryItemViewHolder.views[0]).setImageResource(CommonConstant.RESOUCE_TAG[tag.getDefaultType()]);
        ((EditText) addDiaryItemViewHolder.views[1]).setText(tag.getName());
    }

    @Override
    protected void initEvent(final int position) {
        super.initEvent(position);
        addDiaryItemViewHolder.views[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editItemClickListener != null) {
                    editItemClickListener.onEditItemClick(v, position);
                }
            }
        });

        addDiaryItemViewHolder.views[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteItemClickListener != null) {
                    deleteItemClickListener.onDeleteItemClick(v, position);
                }
            }
        });
    }

    @Override
    public void setOnEditItemClickListener(onEditItemClickListener listener) {
        super.setOnEditItemClickListener(listener);
    }

    @Override
    public void setOnDeleteItemClickListener(onDeleteItemClickListener listener) {
        super.setOnDeleteItemClickListener(listener);
    }
}
