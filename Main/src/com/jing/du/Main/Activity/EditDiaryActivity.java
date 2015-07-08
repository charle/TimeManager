package com.jing.du.Main.Activity;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.jing.du.Main.Adapter.DiaryEditSwipeAdapter;
import com.jing.du.Main.Model.Diary;
import com.jing.du.Main.Model.DiaryItem;
import com.jing.du.Main.R;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.utils.DateUtils;
import com.jing.du.common.view.SwipeListView;
import org.litepal.crud.DataSupport;

/**
 * Created by charle-chen on 15/7/6.
 */
public class EditDiaryActivity extends BaseActivity {

    private Diary diary;
    private DiaryEditSwipeAdapter diaryEditSwipeAdapter;
    private int selectedSpinner;
    private SwipeListView lvDiaryItem;
    private EditText etCreateAddress;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    diaryEditSwipeAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_diary_edit);
        Intent intent = this.getIntent();
        diary = (Diary) intent.getSerializableExtra("diary");
        initView();
    }

    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        View headView = inflater.inflate(R.layout.layout_diary_edit_header, null);

        ((TextView) headView.findViewById(R.id.tv_create_time)).setText(DateUtils.getStringOfDate(diary.getCreateTime()));
        etCreateAddress = ((EditText) headView.findViewById(R.id.et_create_address));
        etCreateAddress.setText(diary.getAddress());
        Spinner spWeather = (Spinner) headView.findViewById(R.id.sp_weather);
        ArrayAdapter weatherAdapter = ArrayAdapter.createFromResource(this, R.array.weather, android.R.layout.simple_spinner_item);
        spWeather.setAdapter(weatherAdapter);
        spWeather.setSelection(diary.getWeatherType(), true);
        spWeather.setOnItemSelectedListener(new SpinnerSelectedListener());
        lvDiaryItem = (SwipeListView) findViewById(R.id.lv_diary_item);
        lvDiaryItem.addHeaderView(headView);

        diaryEditSwipeAdapter = new DiaryEditSwipeAdapter(this, diary.getDiaryItemArrayList(), lvDiaryItem.getRightViewWidth());
        lvDiaryItem.setAdapter(diaryEditSwipeAdapter);
        diaryEditSwipeAdapter.setOnRightItemClickListener(new DiaryEditSwipeAdapter.onRightItemClickListener() {
            @Override
            public void onRightItemClick(View v, final int position) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int diaryId = diary.getDiaryItemArrayList().get(position).getId();
                        DataSupport.delete(DiaryItem.class, diaryId);
                        diary.getDiaryItemArrayList().remove(position);//删除内存数据
                        mHandler.sendEmptyMessage(1);
                    }
                }).start();
            }
        });

        ActionBar actionBar = getActionBar();
        actionBar.show();
    }

    //使用数组形式操作
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            selectedSpinner = arg2;
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_diary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                ContentValues values = new ContentValues();
                values.put("weatherType", selectedSpinner);
                values.put("address", etCreateAddress.getText().toString());
                DataSupport.update(Diary.class, values, diary.getId());
                diary.setWeatherType(selectedSpinner);
                diary.setAddress(etCreateAddress.getText().toString());
                Intent intent = EditDiaryActivity.this.getIntent().putExtra("diary", diary);
                EditDiaryActivity.this.setResult(CommonConstant.GOTO_DIARY_DETAIL, intent);
                EditDiaryActivity.this.finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
