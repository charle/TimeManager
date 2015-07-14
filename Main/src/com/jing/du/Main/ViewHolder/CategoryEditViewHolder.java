package com.jing.du.Main.ViewHolder;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.jing.du.common.view.MyBaseHolder;

/**
 * Created by charle-chen on 15/7/14.
 */
public class CategoryEditViewHolder extends MyBaseHolder {
    private ImageView imageView;
    private EditText editText;
    private BootstrapButton button1,button2;

    public CategoryEditViewHolder() {
        views = new View[4];
        views[0] = imageView;
        views[1] = editText;
        views[2] = button1;
        views[3] = button2;
    }
}
