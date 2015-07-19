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
    public static final int GOTO_DETAIL_MINDER = 9;
    public static final int GOTO_MINDER_FLAGMENT = 10;
    public static final int GOTO_CATEGORY_DETAIL = 11;
    public static final int GOTO_TAG_FRAGMENT = 12;
    public static final int GOTO_EDIT_CATEGORY = 13;
    public static final int GOTO_EDIT_TAG_ITEM = 14;
    public static final int GOTO_MINDER_FLAGMENT_CHANGE = 15;
    public static final int GOTO_CLIP_IMAGE = 16;
    public static final int GOTO_SET_FRAGMENT = 17;
    public static final int GOTO_PICK_IMAGE = 18;
    public static final int GOTO_EDIT_SIGN = 19;
    public static final int GOTO_EDIT_NICKNAME = 20;

    public static final int EDIT_SIGN = 1;
    public static final int EDIT_NICKNAME = 2;

    public static final String EMERGENCY = "紧急";
    public static final String IMPORTANT = "重要";
    public static final String DELAY = "可暂缓";
    public static final String RESOVLED = "已处理";

    public static final int RESOUCE_EMERGENCY = R.drawable.emergency;
    public static final int RESOUCE_IMPORTANT = R.drawable.important;
    public static final int RESOUCE_DELAY = R.drawable.delay;
    public static final int RESOUCE_RESOVLED = R.drawable.resovled;

    public static final int COLOR_EMERGENCY = Color.RED;
    public static final int COLOR_IMPORTANT = Color.rgb(37, 244, 115);
    public static final int COLOR_DELAY = Color.rgb(11, 182, 245);
    public static final int COLOR_RESOVLED = Color.GRAY;

    public static final String[] MINDER_STATE = {EMERGENCY, IMPORTANT, DELAY, RESOVLED};
    public static final int[] MINDER_RESOUCE = {RESOUCE_EMERGENCY, RESOUCE_IMPORTANT, RESOUCE_DELAY, RESOUCE_RESOVLED};
    public static final int[] MINDER_COLOR = {COLOR_EMERGENCY, COLOR_IMPORTANT, COLOR_DELAY, COLOR_RESOVLED};

    public static final int CATEGORY_WORK = 0;
    public static final int CATEGORY_STUDY = 1;
    public static final int CATEGORY_LIFE = 2;
    public static final int CATEGORY_ENTER = 3;
    public static final int CATEGORY_OTHERS = 4;
    public static final int CATEGORY_ANOTHER = 5;

    public static final int RESOUCE_WORK = R.drawable.work;
    public static final int RESOUCE_STUDY = R.drawable.study;
    public static final int RESOUCE_LIFE = R.drawable.life;
    public static final int RESOUCE_ENTER = R.drawable.enter;
    public static final int RESOUCE_OTHERS = R.drawable.others;

    public static final int TAG_WORK = 0;
    public static final int TAG_READING = 1;
    public static final int TAG_SHOPING = 2;
    public static final int TAG_TRAVEL = 3;
    public static final int TAG_BUILD = 4;
    public static final int TAG_MOVIE = 5;
    public static final int TAG_SERIES = 6;
    public static final int TAG_MUSIC = 7;
    public static final int TAG_INTERNET = 8;
    public static final int TAG_ANOTHER = 9;

    public static final int[] RESOUCE_CATEGORY = {RESOUCE_WORK,RESOUCE_STUDY,RESOUCE_LIFE,RESOUCE_ENTER,RESOUCE_OTHERS,RESOUCE_OTHERS};

    public static final int RESOUCE_TAG_WORK = R.drawable.tag_work;
    public static final int RESOUCE_TAG_READING = R.drawable.reading;
    public static final int RESOUCE_TAG_SHOPING = R.drawable.shoping;
    public static final int RESOUCE_TAG_TRAVEL = R.drawable.travel;
    public static final int RESOUCE_TAG_BUILD = R.drawable.sport;
    public static final int RESOUCE_TAG_MOVIE = R.drawable.movie;
    public static final int RESOUCE_TAG_SERIES = R.drawable.tv;
    public static final int RESOUCE_TAG_MUSIC = R.drawable.music;
    public static final int RESOUCE_TAG_INTERNET = R.drawable.internet;
    public static final int RESOUCE_TAG_OTHERS = R.drawable.tag_another;

    public static final int[] RESOUCE_TAG = {RESOUCE_TAG_WORK,RESOUCE_TAG_READING,RESOUCE_TAG_SHOPING,RESOUCE_TAG_TRAVEL,RESOUCE_TAG_BUILD,
    RESOUCE_TAG_MOVIE,RESOUCE_TAG_SERIES,RESOUCE_TAG_MUSIC,RESOUCE_TAG_INTERNET,RESOUCE_TAG_OTHERS};

    public static final String PROFILE_PATH="/sdcard/TimeMananger/header.jpg";

}
