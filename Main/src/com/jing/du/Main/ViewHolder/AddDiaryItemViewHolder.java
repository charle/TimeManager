package com.jing.du.Main.ViewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jing.du.common.view.MyBaseHolder;

/**
 * Created by charle-chen on 15/7/1.
 */
public class AddDiaryItemViewHolder extends MyBaseHolder {
    private TextView textView,textView1,textView2,textView3,textView4;
    private LinearLayout linearLayout1,linearLayout2;
    public AddDiaryItemViewHolder() {
        views = new View[7];
        views[0] = textView;
        views[1] = textView1;
        views[2] = textView2;
        views[3] = textView3;
        views[4] = textView4;
        views[5] = linearLayout1;
        views[6] = linearLayout2;
    }
}
