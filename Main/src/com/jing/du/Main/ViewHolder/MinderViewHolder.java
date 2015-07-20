package com.jing.du.Main.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.jing.du.common.view.MyBaseHolder;

/**
 * Created by charle-chen on 15/7/12.
 */
public class MinderViewHolder extends MyBaseHolder {
    private TextView textView1,textView2;
    private ImageView imageView1,imageView2;

    public MinderViewHolder() {
        views = new View[4];
        views[0] = textView1;
        views[1] = textView2;
        views[2] = imageView1;
        views[3] = imageView2;
    }
}
