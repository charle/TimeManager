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
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.jing.du.Main.Model.Tag;
import com.jing.du.Main.R;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.utils.Toast;
import org.litepal.crud.DataSupport;

/**
 * Created by charle-chen on 15/7/14.
 */
public class EditTagActivity extends BaseActivity {

    @InjectView(R.id.et_tag_name)
    EditText etTagName;
    @InjectView(R.id.bt_edit_tag)
    BootstrapButton btEditTag;

    private int tagId;
    private Tag tag;
    private boolean isSaved = true;
    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (!StringUtils.isObjectEmpty(tag)) {
                        etTagName.setText(tag.getName());
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_tag);
        ButterKnife.inject(this);
        tagId = getIntent().getIntExtra("tag_id", 0);
        afterView();
        initData();
    }

    private void afterView() {
        ActionBar actionBar = getActionBar();
        actionBar.show();
    }

    @Override
    public void initData() {
        super.initData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                tag = DataSupport.find(Tag.class, tagId);
                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }

    @OnClick({R.id.bt_edit_tag})
    public void clickEvent(View v) {
        switch (v.getId()) {
            case R.id.bt_edit_tag:
                if (isSaved) {
                    etTagName.setEnabled(true);
                    btEditTag.setText(getString(R.string.save));
                    isSaved = false;
                } else {
                    etTagName.setEnabled(false);
                    btEditTag.setText(getString(R.string.edit));
                    isSaved = true;
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_tag, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                String str = etTagName.getText().toString();
                if (StringUtils.isEmpty(str)) {
                    Toast.show(this, getString(R.string.empty_tag_warning), 1000);
                } else {
                    ContentValues values = new ContentValues();
                    values.put("name", str);
                    DataSupport.update(Tag.class, values, tagId);
                    tag.setName(str);
                    Intent intent = getIntent();
                    intent.putExtra("tag", tag);
                    EditTagActivity.this.setResult(CommonConstant.GOTO_EDIT_CATEGORY, intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                break;
        }
        return true;
    }
}
