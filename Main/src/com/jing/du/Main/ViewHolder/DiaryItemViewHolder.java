package com.jing.du.Main.ViewHolder;

import android.view.View;
import android.widget.TextView;
import com.jing.du.common.view.MyBaseHolder;

/**
 * Created by charle-chen on 15/6/29.
 */
public class DiaryItemViewHolder extends MyBaseHolder {
    private TextView textView1,textView2,textView3;

    public DiaryItemViewHolder() {
        views = new View[3];
        views[0] = textView1;
        views[1] = textView2;
        views[2] = textView3;
    }
}
