package com.jing.du.Main.ViewHolder;

import android.view.View;
import android.widget.TextView;
import com.jing.du.common.view.MyBaseHolder;

/**
 * Created by charle-chen on 15/6/30.
 */
public class SpinnerViewHolder extends MyBaseHolder {
    private TextView textView;

    public SpinnerViewHolder() {
        views = new View[1];
        views[0] = textView;
    }
}
