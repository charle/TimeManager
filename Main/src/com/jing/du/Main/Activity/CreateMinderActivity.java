package com.jing.du.Main.Activity;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.jing.du.Main.Model.Minder;
import com.jing.du.Main.R;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.utils.DateTimePickDialogUtil;
import com.jing.du.common.utils.DateUtils;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.view.SwitchView;
import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by charle-chen on 15/7/12.
 */
public class CreateMinderActivity extends BaseActivity {

    @InjectView(R.id.et_minder_title)
    EditText etMinderTitle;
    @InjectView(R.id.sp_minder_type)
    Spinner spMinderType;
    @InjectView(R.id.et_minder_content)
    EditText etMinderContent;
    @InjectView(R.id.et_mind_time)
    EditText etMindTime;
    @InjectView(R.id.tv_mind_time)
    FontAwesomeText tvMindTime;
    @InjectView(R.id.sv_lock)
    SwitchView svLock;
    private DateTimePickDialogUtil dateTimePicKDialog;
    private int minderTypeSpinnerId = 0;
    private Minder minder;
    private int minderId;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    etMinderTitle.setText(minder.getTitle());
                    etMinderContent.setText(minder.getContent());
                    minderTypeSpinnerId = minder.getMinderType();
                    spMinderType.setSelection(minderTypeSpinnerId);
                    etMindTime.setText(DateUtils.getChieseDate(minder.getMindTime()));
                    svLock.setSwitchStatus(minder.getMindOrNotMind() == 1 ? true : false);
                    break;
                case 2:
                    Intent intent = CreateMinderActivity.this.getIntent();
                    intent.putExtra("minder", minder);
                    CreateMinderActivity.this.setResult(CommonConstant.GOTO_MINDER_FLAGMENT, intent);
                    CreateMinderActivity.this.finish();
                    CreateMinderActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_minder);
        ButterKnife.inject(this);
        afterView();
    }

    private void afterView() {
        ArrayAdapter minderAdapter = ArrayAdapter.createFromResource(this, R.array.minder_type, android.R.layout.simple_spinner_item);
        spMinderType.setAdapter(minderAdapter);
        spMinderType.setSelection(0, true);
        ActionBar actionBar = getActionBar();
        actionBar.show();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initData();
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @Override
    public void initData() {
        super.initData();
        Intent intent = getIntent();
        if (!StringUtils.isObjectEmpty(intent)) {
            minderId = intent.getIntExtra("minder_id", 0);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    minder = DataSupport.find(Minder.class, minderId);
                    if (!StringUtils.isObjectEmpty(minder)) {
                        mHandler.sendEmptyMessage(1);
                    }
                }
            }).start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_minder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!StringUtils.isObjectEmpty(minder)) {
                            ContentValues values = new ContentValues();
                            values.put("minderType", minderTypeSpinnerId);
                            values.put("title", etMinderTitle.getText().toString());
                            values.put("content", etMinderContent.getText().toString());
                            if (!StringUtils.isObjectEmpty(dateTimePicKDialog)) {
                                values.put("mindtime", dateTimePicKDialog.getMindTime().getTime());
                                minder.setMindTime(dateTimePicKDialog.getMindTime());
                            }
                            values.put("mindornotmind", svLock.getSwitchStatus() ? 1 : 0);
                            DataSupport.update(Minder.class, values, minder.getId());
                            minder.setTitle(etMinderTitle.getText().toString());
                            minder.setContent(etMinderContent.getText().toString());
                            minder.setMinderType(minderTypeSpinnerId);
                            minder.setMindOrNotMind(svLock.getSwitchStatus() ? 1 : 0);
                        } else {
                            minder = new Minder();
                            minder.setCreatetime(new Date());
                            minder.setTitle(etMinderTitle.getText().toString());
                            minder.setContent(etMinderContent.getText().toString());
                            minder.setMinderType(minderTypeSpinnerId);
                            minder.setMindTime(dateTimePicKDialog.getMindTime());
                            minder.setMindOrNotMind(svLock.getSwitchStatus() ? 1 : 0);
                            minder.save();
                        }
                        mHandler.sendEmptyMessage(2);
                    }
                }).start();
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

    @OnItemSelected({R.id.sp_minder_type})
    void weatherSpinnerItemSelected(int position) {
        minderTypeSpinnerId = position;
    }

    @OnClick({R.id.tv_mind_time})
    public void clickEvent(View v) {
        switch (v.getId()) {
            case R.id.tv_mind_time:
                dateTimePicKDialog = new DateTimePickDialogUtil(
                        CreateMinderActivity.this, null);
                dateTimePicKDialog.dateTimePicKDialog(etMindTime);
        }

    }
}

