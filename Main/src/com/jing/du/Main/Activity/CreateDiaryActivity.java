package com.jing.du.Main.Activity;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.jing.du.Main.Adapter.AddDiaryItemAdapter;
import com.jing.du.Main.Adapter.MySpinnerAdapter;
import com.jing.du.Main.Model.Category;
import com.jing.du.Main.Model.Diary;
import com.jing.du.Main.Model.DiaryItem;
import com.jing.du.Main.Model.Tag;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.AddDiaryItemViewHolder;
import com.jing.du.Main.ViewHolder.SpinnerViewHolder;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.utils.Log;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.utils.Toast;
import com.jing.du.common.view.MyInnerListView;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by charle-chen on 15/6/29.
 */
public class CreateDiaryActivity extends BaseActivity {

    @InjectView(R.id.lv_diary_item)
    MyInnerListView lvDiaryItem;
    @InjectView(R.id.sp_weather)
    Spinner spWeather;
    @InjectView(R.id.et_create_address)
    EditText etCreateAddress;
    @InjectView(R.id.sp_one)
    Spinner spOne;
    @InjectView(R.id.sp_two)
    Spinner spTwo;
    @InjectView(R.id.et_begin_time)
    EditText etBeginTime;
    @InjectView(R.id.tv_begin_time)
    FontAwesomeText tvBeginTime;
    @InjectView(R.id.et_end_time)
    EditText etEndTime;
    @InjectView(R.id.tv_end_time)
    FontAwesomeText tvEndTime;
    @InjectView(R.id.et_notice)
    EditText etNotice;
    @InjectView(R.id.bt_create_diary)
    BootstrapButton btCreateDiary;
    @InjectView(R.id.lv_add_diary_item)
    LinearLayout lvAddDiaryItem;
    @InjectView(R.id.ll_diary_header)
    LinearLayout llDiaryHeader;

    private List<Category> categoryList = new ArrayList<Category>();
    private int oneSpinnerId;
    private int twoSpinnerId;
    private int onePosition;
    private int weatherSpinnerId;
    private MySpinnerAdapter spinnerAdapter;
    private Diary diary;
    private AddDiaryItemAdapter addDiaryItemAdapter;
    private List<DiaryItem> diaryItems = new ArrayList<DiaryItem>();
    private boolean diaryItemAddedFlag = false;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    SpinnerViewHolder spinnerViewHolder = new SpinnerViewHolder();
                    SpinnerViewHolder spinnerViewHolder1 = new SpinnerViewHolder();
                    if (!StringUtils.isListEmpty(categoryList)) {
                        MySpinnerAdapter spinnerAdapter = new MySpinnerAdapter(CreateDiaryActivity.this, categoryList, spinnerViewHolder, R.layout.spinner_item, CommonConstant.SPINNER_ONE_ID) {
                        };
                        spOne.setAdapter(spinnerAdapter);
                        onePosition = 0;
                        oneSpinnerId = ((Category) spinnerAdapter.getItem(0)).getId();
                    }
                    if (!StringUtils.isListEmpty(categoryList)) {
                        MySpinnerAdapter spinnerAdapter1 = new MySpinnerAdapter(CreateDiaryActivity.this, categoryList.get(0).getTagList(), spinnerViewHolder1, R.layout.spinner_item, CommonConstant.SPINNER_TWO_ID) {
                        };
                        spTwo.setAdapter(spinnerAdapter1);
                        twoSpinnerId = ((Tag) spinnerAdapter1.getItem(0)).getId();
                    }
                    break;
                case 2:
                    lvAddDiaryItem.setVisibility(View.INVISIBLE);
                    llDiaryHeader.setVisibility(View.GONE);
                    Toast.show(CreateDiaryActivity.this, "成功", 1000);
                    addDiaryItemAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_create_diary);
        ButterKnife.inject(this);
        Intent intent = this.getIntent();
        diary = (Diary) intent.getSerializableExtra("diary");
        if (!StringUtils.isObjectEmpty(diary)) {
            diaryItems = diary.getDiaryItemArrayList();
            diaryItemAddedFlag = true;
        } else {
            diary = new Diary();
        }
        Log.d(diary.getId() + ">>>>>>>>>>>>>>>>>>>>>>");
        afterInitView();
    }

    private void afterInitView() {
        ActionBar actionBar = getActionBar();
        actionBar.show();
        initData();
    }

    @Override
    public void initData() {
        super.initData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                categoryList = DataSupport.findAll(Category.class, true);
                mHandler.sendEmptyMessage(1);
            }
        }).start();
        AddDiaryItemViewHolder addDiaryItemViewHolder = new AddDiaryItemViewHolder();
        addDiaryItemAdapter = new AddDiaryItemAdapter(this, diaryItems, addDiaryItemViewHolder, R.layout.add_diary_item);
        lvDiaryItem.setAdapter(addDiaryItemAdapter);

        ArrayAdapter weatherAdapter = ArrayAdapter.createFromResource(this, R.array.weather, android.R.layout.simple_spinner_item);
        spWeather.setAdapter(weatherAdapter);
        spWeather.setSelection(0, true);
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @OnClick({R.id.tv_begin_time, R.id.tv_end_time, R.id.bt_create_diary})
    public void clickEvent(View v) {
        switch (v.getId()) {
            case R.id.tv_begin_time:
                Calendar calendar = Calendar.getInstance();
                TimePickerDialog.OnTimeSetListener timeListener =
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timerPicker,
                                                  int hourOfDay, int minute) {
                                etBeginTime.setText((hourOfDay < 10 ? "0" + hourOfDay : hourOfDay) + ":" + (minute < 10 ? "0" + minute : minute));
                            }
                        };
                final Dialog dialog = new TimePickerDialog(this, timeListener,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false);
                dialog.show();
                break;
            case R.id.tv_end_time:
                Calendar calendar1 = Calendar.getInstance();
                TimePickerDialog.OnTimeSetListener timeListener1 =
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker timerPicker,
                                                  int hourOfDay, int minute) {
                                etEndTime.setText((hourOfDay < 10 ? "0" + hourOfDay : hourOfDay) + ":" + (minute < 10 ? "0" + minute : minute));
                            }
                        };
                Dialog dialog1 = new TimePickerDialog(this, timeListener1,
                        calendar1.get(Calendar.HOUR_OF_DAY),
                        calendar1.get(Calendar.MINUTE),
                        false);
                dialog1.show();
                break;
            case R.id.bt_create_diary:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!diaryItemAddedFlag) {
                            diary.setCreateTime(new Date());
                            diary.setAddress(etCreateAddress.getText().toString());
                            diary.setWeatherType(weatherSpinnerId);
                            diary.save();
                            diaryItemAddedFlag = true;
                        }
                        Category category = DataSupport.find(Category.class, oneSpinnerId);
                        DiaryItem diaryItem = new DiaryItem();
                        diaryItem.setBeginTime(etBeginTime.getText().toString());
                        diaryItem.setEndTime(etEndTime.getText().toString());
                        diaryItem.setTag(DataSupport.find(Tag.class, twoSpinnerId));
                        diaryItem.setCategory(category);
                        diaryItem.setNote(etNotice.getText().toString());
                        diaryItem.setDiary(DataSupport.find(Diary.class, diary.getId()));
                        diaryItem.save();
                        diaryItems.add(diaryItem);
                        mHandler.sendEmptyMessage(2);
                    }
                }).start();
                break;
        }
    }

    @OnItemSelected({R.id.sp_one})
    void onSpinnerOneItemSelected(int position) {
        if (!StringUtils.isObjectEmpty(categoryList.get(position))) {
            oneSpinnerId = categoryList.get(position).getId();
            onePosition = position;
            if (!StringUtils.isListEmpty(categoryList.get(position).getTagList())) {
                SpinnerViewHolder spinnerViewHolder1 = new SpinnerViewHolder();
                MySpinnerAdapter spinnerAdapter1 = new MySpinnerAdapter(CreateDiaryActivity.this, categoryList.get(position).getTagList(), spinnerViewHolder1, R.layout.spinner_item, CommonConstant.SPINNER_TWO_ID) {
                };
                twoSpinnerId = categoryList.get(position).getTagList().get(0).getId();
                spTwo.setAdapter(spinnerAdapter1);
            }

        }
    }

    @OnItemSelected({R.id.sp_two})
    void onSpinnerTwoItemSelected(int position) {
        if (!StringUtils.isObjectEmpty(categoryList.get(onePosition)) && !StringUtils.isListEmpty(categoryList.get(onePosition).getTagList())) {
            twoSpinnerId = categoryList.get(onePosition).getTagList().get(position).getId();
        }
    }

    @OnItemSelected({R.id.sp_weather})
    void weatherSpinnerItemSelected(int position){
        weatherSpinnerId = position;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_diary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                lvAddDiaryItem.setVisibility(View.VISIBLE);
                break;
            case R.id.action_save:
                if (!diaryItemAddedFlag) {
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
                } else {
                    Intent intent = CreateDiaryActivity.this.getIntent().putExtra("diary", diary);
                    CreateDiaryActivity.this.setResult(CommonConstant.GOTO_HOME_FLAGMENT, intent);
                    CreateDiaryActivity.this.finish();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CommonConstant.GOTO_DIARY_DETAIL:
                if (resultCode == CommonConstant.GOTO_HOME_FLAGMENT) {

                }
            default:
                break;
        }
    }
}
