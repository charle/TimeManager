package com.jing.du.Main.Activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
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

    private List<Category> categoryList = new ArrayList<Category>();
    private int oneSpinnerId;
    private int twoSpinnerId;
    private int onePosition;
    private MySpinnerAdapter spinnerAdapter;
    private Diary diary = new Diary();
    private AddDiaryItemAdapter addDiaryItemAdapter;
    private List<DiaryItem> diaryItems = new ArrayList<DiaryItem>();

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
        afterInitView();
        Log.d("activity is created");
    }

    private void afterInitView() {
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
                        Category category = DataSupport.find(Category.class, oneSpinnerId);
                        diary.setCreateTime(new Date());
                        DiaryItem diaryItem = new DiaryItem();
                        diaryItem.setBeginTime(etBeginTime.getText().toString());
                        diaryItem.setEndTime(etEndTime.getText().toString());
                        diaryItem.setTag(DataSupport.find(Tag.class, twoSpinnerId));
                        diaryItem.setCategory(category);
                        diaryItem.setNote(etNotice.getText().toString());
                        diaryItem.setDiary(diary);
                        diaryItem.save();
                        diary.save();
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
