package com.jing.du.Main.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.jing.du.common.view.MyBaseHolder;
import com.jing.du.common.view.MyInnerListView;

/**
 * Created by charle-chen on 15/6/27.
 */
public class DiaryViewHolder extends MyBaseHolder {
    private TextView textView1,textView2,textView3,textView4,textView5;
    private ImageView imageView;

    public DiaryViewHolder(){
        this.views = new View[6];
        views[0] = textView1;
        views[1] = textView2;
        views[2] = textView3;
        views[3] = textView4;
        views[4] = textView5;
        views[5] = imageView;
    }

}
