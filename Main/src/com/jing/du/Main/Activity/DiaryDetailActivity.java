package com.jing.du.Main.Activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
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
    private int diaryId;
    private Diary diary;
    private int   listPosition;
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
        setContentView(R.layout.layout_diary_detail);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        diaryId = bundle.getInt("diary_id", 0);
        listPosition = bundle.getInt("list_position",0);
        afterView();
    }


    private void afterView() {
        ActionBar actionBar = getActionBar();
        actionBar.show();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.diary_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("diary_id", diaryId);
                intent.putExtras(bundle);
                intent.setClass(this, EditDiaryActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.action_delete:
                dialog();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认删除日志吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                DataSupport.delete(Diary.class, diaryId);
                Intent intent = DiaryDetailActivity.this.getIntent().putExtra("listPosition", listPosition);
                DiaryDetailActivity.this.setResult(CommonConstant.GOTO_HOME_FLAGMENT,intent);
                DiaryDetailActivity.this.finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
