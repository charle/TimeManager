package com.jing.du.Main.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.jing.du.Main.Adapter.AddDiaryItemAdapter;
import com.jing.du.Main.Model.Diary;
import com.jing.du.Main.Model.DiaryItem;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.AddDiaryItemViewHolder;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.utils.DateUtils;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charle-chen on 15/7/3.
 */
public class DiaryDetailActivity extends BaseActivity {

    @InjectView(R.id.lv_diary_item)
    ListView lvDiaryItem;

    FontAwesomeText tvMenu;

    private int diaryId;
    private Diary diary;
    private List<DiaryItem> diaryItemList = new ArrayList<DiaryItem>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    initView();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.layout_diary_detail);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        diaryId = bundle.getInt("diary_id", 0);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.my_title);
        afterView();
    }


    private void afterView() {
        tvMenu = (FontAwesomeText)findViewById(R.id.tv_menu);
        tvMenu.setOnClickListener(menuClick);
        initData();
    }

    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        View headView = inflater.inflate(R.layout.layout_diary_detail_header, null);
        ((TextView) headView.findViewById(R.id.tv_create_time)).setText(DateUtils.getStringOfDate(diary.getCreateTime()));
        ((TextView) headView.findViewById(R.id.tv_create_address)).setText(DateUtils.getStringOfDate(diary.getCreateTime()));
        ((ImageView) headView.findViewById(R.id.iv_create_weather)).setImageResource(CommonConstant.WEATHER_RESOUCE[diary.getWeatherType()]);
        lvDiaryItem.addHeaderView(headView);
        AddDiaryItemViewHolder addDiaryItemViewHolder = new AddDiaryItemViewHolder();
        AddDiaryItemAdapter addDiaryItemAdapter = new AddDiaryItemAdapter(this, diaryItemList, addDiaryItemViewHolder, R.layout.detail_diary_item);
        lvDiaryItem.setAdapter(addDiaryItemAdapter);
    }

    @Override
    public void initData() {
        super.initData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                diary = DataSupport.find(Diary.class, diaryId, true);
                diaryItemList = diary.getDiaryItemArrayList();
                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }


    View.OnClickListener menuClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
