package com.jing.du.Main.Activity;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.jing.du.Main.Model.Diary;
import com.jing.du.Main.R;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.utils.DateUtils;
import org.litepal.crud.DataSupport;

/**
 * Created by charle-chen on 15/7/6.
 */
public class EditDiaryActivity extends BaseActivity {

    private Diary diary;
    private int selectedSpinner;
    @InjectView(R.id.tv_create_time)
    TextView tvCreateTime;
    @InjectView(R.id.sp_weather)
    Spinner spWeather;
    @InjectView(R.id.et_create_address)
    EditText etCreateAddress;
    @InjectView(R.id.lv_diary_item)
    ListView lvDiaryItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_diary_edit);
        ButterKnife.inject(this);
        Intent intent = this.getIntent();
        diary = (Diary) intent.getSerializableExtra("diary");
        initView();
    }

    private void initView() {
        tvCreateTime.setText(DateUtils.getStringOfDate(diary.getCreateTime()));
        etCreateAddress.setText(diary.getAddress());
        ArrayAdapter weatherAdapter = ArrayAdapter.createFromResource(this, R.array.weather, android.R.layout.simple_spinner_item);
        spWeather.setAdapter(weatherAdapter);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWeather.setSelection(diary.getWeatherType(), true);
        spWeather.setOnItemSelectedListener(new SpinnerSelectedListener());
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
                values.put("weatherType",selectedSpinner);
                values.put("address",etCreateAddress.getText().toString());
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
