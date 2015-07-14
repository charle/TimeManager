package com.jing.du.Main.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jing.du.Main.Model.DiaryItem;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.AddDiaryItemViewHolder;
import com.jing.du.common.adapter.SwipeListBaseAdapter;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.utils.Log;
import com.jing.du.common.utils.StringUtils;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by charle-chen on 15/7/8.
 */
public class DiaryEditSwipeAdapter extends SwipeListBaseAdapter {

    private AddDiaryItemViewHolder addDiaryItemViewHolder;
    private int rightWith;

    public DiaryEditSwipeAdapter(Context mContext, List<? extends Object> listData, int rightWidth) {
        super(mContext, listData);
        this.rightWith = rightWidth;
    }

    @Override
    protected View initView(View converView) {
        if (!StringUtils.isViewEmpty(converView)) {
            addDiaryItemViewHolder = (AddDiaryItemViewHolder) converView.getTag();
        } else {
            addDiaryItemViewHolder = new AddDiaryItemViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            converView = mInflater.inflate(R.layout.edit_diary_item, null);
            addDiaryItemViewHolder.views[0] = (TextView) converView.findViewById(R.id.category_name);
            addDiaryItemViewHolder.views[1] = (TextView) converView.findViewById(R.id.tag_name);
            addDiaryItemViewHolder.views[2] = (TextView) converView.findViewById(R.id.tv_begin_time);
            addDiaryItemViewHolder.views[3] = (TextView) converView.findViewById(R.id.tv_end_time);
            addDiaryItemViewHolder.views[4] = (TextView) converView.findViewById(R.id.tv_note);
            addDiaryItemViewHolder.views[5] = (LinearLayout) converView.findViewById(R.id.ll_main_part);
            addDiaryItemViewHolder.views[6] = (LinearLayout) converView.findViewById(R.id.ll_left_part);
            addDiaryItemViewHolder.views[7] = (LinearLayout) converView.findViewById(R.id.ll_right_part);
            addDiaryItemViewHolder.views[8] = (ImageView) converView.findViewById(R.id.iv_tag_icon);
            converView.setTag(addDiaryItemViewHolder);
        }

        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        addDiaryItemViewHolder.views[5].setLayoutParams(lp1);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(rightWith/2, LinearLayout.LayoutParams.MATCH_PARENT);
        addDiaryItemViewHolder.views[6].setLayoutParams(lp2);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(rightWith/2, LinearLayout.LayoutParams.MATCH_PARENT);
        addDiaryItemViewHolder.views[7].setLayoutParams(lp3);
        return converView;
    }

    @Override
    protected void initData(int position) {
        DiaryItem diaryItem = (DiaryItem) listData.get(position);
        DiaryItem item = DataSupport.find(DiaryItem.class, diaryItem.getId(), true);
        ((TextView) addDiaryItemViewHolder.views[0]).setText(item.getCategory().getName());
        ((TextView) addDiaryItemViewHolder.views[1]).setText(item.getTag().getName());
        ((TextView) addDiaryItemViewHolder.views[2]).setText(item.getBeginTime());
        ((TextView) addDiaryItemViewHolder.views[3]).setText(item.getEndTime());
        ((TextView) addDiaryItemViewHolder.views[4]).setText(item.getNote());
        ((ImageView)addDiaryItemViewHolder.views[8]).setImageResource(CommonConstant.RESOUCE_TAG[item.getTag().getDefaultType()]);
        Log.d("diary-edit-swipe-adpate data inited");
    }


    @Override
    protected void initEvent(final int position) {
        addDiaryItemViewHolder.views[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftItemClickListener != null) {
                    leftItemClickListener.onLeftItemClick(v, position);
                }
            }
        });

        addDiaryItemViewHolder.views[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightItemClickListener != null) {
                    rightItemClickListener.onRightItemClick(v, position);
                }
            }
        });
    }

    @Override
    public void setOnRightItemClickListener(onRightItemClickListener listener) {
        super.setOnRightItemClickListener(listener);
    }

    @Override
    public void setOnLeftItemClickListener(onLeftItemClickListener listener) {
        super.setOnLeftItemClickListener(listener);
    }
}
