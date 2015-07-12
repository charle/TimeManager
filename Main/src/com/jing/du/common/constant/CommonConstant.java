package com.jing.du.common.constant;

import android.graphics.Color;
import com.jing.du.Main.R;

/**
 * Created by charle-chen on 15/6/30.
 */
public class CommonConstant {
    public final static int SPINNER_ONE_ID = 1;
    public final static int SPINNER_TWO_ID = 2;

    public static final int SUNNY = 1;
    public static final int CLOUDY = 2;
    public static final int OVERCAST = 3;
    public static final int RAINNY = 4;

    public static final int RESOUCE_SUNNY = R.drawable.sunny;
    public static final int RESOUCE_CLOUDY = R.drawable.overcast;
    public static final int RESOUCE_OVERCAST = R.drawable.cloudy;
    public static final int RESOUCE_RAINNY = R.drawable.rainy;

    public static final String WEATHER_SUNNY = "睛天";
    public static final String WEATHER_CLOUDY = "多云";
    public static final String WEATHER_OVERCAST = "阴天";
    public static final String WEATHER_RAINNY = "雨天";

    public static final int[] WEATHER_RESOUCE = {RESOUCE_SUNNY, RESOUCE_CLOUDY, RESOUCE_OVERCAST, RESOUCE_RAINNY};
    public static final String[] WEATHER_STATE = {WEATHER_SUNNY, WEATHER_CLOUDY, WEATHER_OVERCAST, WEATHER_RAINNY};

    public static final int GOTO_DIARY_DETAIL = 1;
    public static final int GOTO_HOME_FLAGMENT = 2;
    public static final int GOTO_CREATE_DIARY = 3;
    public static final int GOTO_EDIT_DIARY = 4;
    public static final int GOTO_HOME_FLAGMENT_FROM_EDIT_DIARY = 5;
    public static final int GOTO_EDIT_DIARY_ITEM = 6;
    public static final int GOTO_DETATIL_FROM_EDIT_DIARY = 7;
    public static final int GOTO_CREATE_MINDER = 8;

    public static final int RESOUCE_EMERGENCY = R.drawable.emergency;
    public static final int RESOUCE_IMPORTANT = R.drawable.important;
    public static final int RESOUCE_DELAY = R.drawable.delay;
    public static final int RESOUCE_RESOVLED = R.drawable.resovled;

    public static final int COLOR_EMERGENCY = Color.RED;
    public static final int COLOR_IMPORTANT = Color.rgb(37,244,115);
    public static final int COLOR_DELAY = Color.rgb(11,182,245);
    public static final int COLOR_RESOVLED = Color.GRAY;

    public static final int[] MINDER_RESOUCE = {RESOUCE_EMERGENCY,RESOUCE_IMPORTANT,RESOUCE_DELAY,RESOUCE_RESOVLED};
    public static final int[] MINDER_COLOR = {COLOR_EMERGENCY,COLOR_IMPORTANT,COLOR_DELAY,COLOR_RESOVLED};
}
