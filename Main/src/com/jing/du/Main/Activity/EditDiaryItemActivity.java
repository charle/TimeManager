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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.jing.du.Main.Adapter.MySpinnerAdapter;
import com.jing.du.Main.Model.Category;
import com.jing.du.Main.Model.DiaryItem;
import com.jing.du.Main.Model.Tag;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.SpinnerViewHolder;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.utils.StringUtils;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by charle-chen on 15/7/9.
 */
public class EditDiaryItemActivity extends BaseActivity {

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

    private DiaryItem diaryItem;
    private int oneSpinnerId;
    private int twoSpinnerId;
    private int firstSelected = 0;
    private int secondSelected = 0;
    private int onePosition = 0;
    private List<Category> categoryList = new ArrayList<Category>();
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    SpinnerViewHolder spinnerViewHolder = new SpinnerViewHolder();
                    SpinnerViewHolder spinnerViewHolder1 = new SpinnerViewHolder();
                    if (!StringUtils.isListEmpty(categoryList)) {
                        MySpinnerAdapter spinnerAdapter = new MySpinnerAdapter(EditDiaryItemActivity.this, categoryList, spinnerViewHolder, R.layout.spinner_item, CommonConstant.SPINNER_ONE_ID) {
                        };
                        spOne.setAdapter(spinnerAdapter);
                        spOne.setSelection(firstSelected);
                        oneSpinnerId = ((Category) spinnerAdapter.getItem(firstSelected)).getId();
                    }
                    if (!StringUtils.isListEmpty(categoryList)) {
                        MySpinnerAdapter spinnerAdapter1 = new MySpinnerAdapter(EditDiaryItemActivity.this, categoryList.get(firstSelected).getTagList(), spinnerViewHolder1, R.layout.spinner_item, CommonConstant.SPINNER_TWO_ID) {
                        };
                        spTwo.setAdapter(spinnerAdapter1);
                        spTwo.setSelection(secondSelected);
                        twoSpinnerId = ((Tag) spinnerAdapter1.getItem(secondSelected)).getId();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_diary_item);
        ButterKnife.inject(this);
        Intent intent = this.getIntent();
        diaryItem = (DiaryItem) intent.getSerializableExtra("diary_item");
        if (!StringUtils.isObjectEmpty(diaryItem)) {
            diaryItem = DataSupport.find(DiaryItem.class, diaryItem.getId(), true);
        }
        afterInitView();
    }

    private void afterInitView() {
        ActionBar actionBar = getActionBar();
        actionBar.show();
        actionBar.setDisplayHomeAsUpEnabled(true);
        etBeginTime.setText(diaryItem.getBeginTime());
        etEndTime.setText(diaryItem.getEndTime());
        etNotice.setText(diaryItem.getNote());
        initData();
    }

    @Override
    public void initData() {
        super.initData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                categoryList = DataSupport.findAll(Category.class, true);
                for (int i = 0; i < categoryList.size(); i++) {
                    if (categoryList.get(i).getName().trim().equals(diaryItem.getCategory().getName())) {
                        firstSelected = i;
                        break;
                    }
                }
                for (int j = 0; j < categoryList.get(firstSelected).getTagList().size(); j++) {
                    if (categoryList.get(firstSelected).getTagList().get(j).getName().equals(diaryItem.getTag().getName())) {
                        secondSelected = j;
                    }
                }
                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }

    @OnClick({R.id.tv_begin_time, R.id.tv_end_time})
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
        }
    }

    @OnItemSelected({R.id.sp_one})
    void onSpinnerOneItemSelected(int position) {
        if (!StringUtils.isObjectEmpty(categoryList.get(position))) {
            oneSpinnerId = categoryList.get(position).getId();
            onePosition = position;
            if (!StringUtils.isListEmpty(categoryList.get(position).getTagList())) {
                SpinnerViewHolder spinnerViewHolder1 = new SpinnerViewHolder();
                MySpinnerAdapter spinnerAdapter1 = new MySpinnerAdapter(EditDiaryItemActivity.this, categoryList.get(position).getTagList(), spinnerViewHolder1, R.layout.spinner_item, CommonConstant.SPINNER_TWO_ID) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_diary_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                diaryItem.setBeginTime(etBeginTime.getText().toString());
                diaryItem.setEndTime(etEndTime.getText().toString());
                diaryItem.setNote(etNotice.getText().toString());
                diaryItem.setCategory(DataSupport.find(Category.class, oneSpinnerId));
                diaryItem.setTag(DataSupport.find(Tag.class, twoSpinnerId));
                diaryItem.update(diaryItem.getId());
                Intent intent = EditDiaryItemActivity.this.getIntent().putExtra("diary_item", diaryItem);
                EditDiaryItemActivity.this.setResult(CommonConstant.GOTO_EDIT_DIARY, intent);
                EditDiaryItemActivity.this.finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
