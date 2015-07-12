package com.jing.du.Main.Activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemSelected;
import com.jing.du.Main.Model.Minder;
import com.jing.du.Main.R;

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

    private int minderTypeSpinnerId = 0;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
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
        initData();
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @Override
    public void initData() {
        super.initData();
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
                        Minder minder = new Minder();
                        minder.setCreatetime(new Date());
                        minder.setTitle(etMinderTitle.getText().toString());
                        minder.setContent(etMinderContent.getText().toString());
                        minder.setMinderType(minderTypeSpinnerId);
                        minder.save();
                        mHandler.sendEmptyMessage(1);
                    }
                }).start();
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
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


}

