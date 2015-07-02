package com.jing.du.Main.ViewHolder;

import android.view.View;
import android.widget.TextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.jing.du.common.view.MyBaseHolder;

/**
 * Created by charle-chen on 15/6/26.
 */
public class TagViewHolder extends MyBaseHolder {
    private BootstrapButton button1, button2;
    private TextView textView;

    public TagViewHolder() {
        views = new View[3];
        views[0] = textView;
        views[1] = button1;
        views[2] = button2;
    }
}
