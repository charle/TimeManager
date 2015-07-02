package com.jing.du.Main.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.jing.du.common.view.MyBaseHolder;
import com.jing.du.common.view.MyInnerListView;

import java.util.List;

/**
 * Created by charle-chen on 15/6/25.
 */
public class CategoyViewHolder extends MyBaseHolder {
    private BootstrapButton button1,button2,button3;
    private MyInnerListView listView;
    private TextView textView;

    public CategoyViewHolder(){
        views = new View[6];
        views[0]=textView;
        views[1]=button1;
        views[2]=button2;
        views[3]=button3;
        views[4]=listView;
    }
}
