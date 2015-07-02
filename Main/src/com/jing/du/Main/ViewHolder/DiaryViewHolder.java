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
    private TextView textView1,textView2;
    private MyInnerListView listView;
    private ImageView imageView;

    public DiaryViewHolder(){
        this.views = new View[4];
        views[0] = textView1;
        views[1] = textView2;
        views[2] = listView;
        views[3] = imageView;
    }

}
