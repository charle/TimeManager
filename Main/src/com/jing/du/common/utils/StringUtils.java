package com.jing.du.common.utils;

import android.view.View;

import java.util.List;

/**
 * Created by charle-chen on 15/6/25.
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isViewEmpty(View view){
        return view == null;
    }

    public static boolean isListEmpty(List<? extends Object> list){
        return list ==null|| list.size()==0;
    }

    public static boolean isObjectEmpty(Object object) {
        return object == null;
    }

}
