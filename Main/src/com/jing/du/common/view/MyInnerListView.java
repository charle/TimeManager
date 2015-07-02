package com.jing.du.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by charle-chen on 15/6/29.
 */
public class MyInnerListView extends ListView {

    public MyInnerListView(Context context) {
        super(context);
    }

    public MyInnerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyInnerListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
