package com.jing.du.Main.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    private TextView textView;
    private ImageView imageView;
    public CategoyViewHolder(){
        views = new View[2];
        views[0]=textView;
        views[1]=imageView;
    }
}
