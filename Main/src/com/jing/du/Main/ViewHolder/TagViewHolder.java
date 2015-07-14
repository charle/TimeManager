package com.jing.du.Main.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.jing.du.common.view.MyBaseHolder;

/**
 * Created by charle-chen on 15/6/26.
 */
public class TagViewHolder extends MyBaseHolder {
    private TextView textView;
    private ImageView imageView;

    public TagViewHolder() {
        views = new View[2];
        views[0] = textView;
        views[1] = imageView;
    }
}
