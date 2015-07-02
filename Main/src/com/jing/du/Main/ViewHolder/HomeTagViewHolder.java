package com.jing.du.Main.ViewHolder;

import android.view.View;
import android.widget.TextView;
import com.jing.du.common.view.MyBaseHolder;

/**
 * Created by charle-chen on 15/6/29.
 */
public class HomeTagViewHolder extends MyBaseHolder {
    private TextView textView1, textView2;

    public HomeTagViewHolder() {
        this.views = new View[2];
        views[0] = textView1;
        views[1] = textView2;
    }
}
