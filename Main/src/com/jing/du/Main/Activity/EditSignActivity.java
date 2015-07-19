package com.jing.du.Main.Activity;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.jing.du.Main.Model.User;
import com.jing.du.Main.R;
import com.jing.du.MainApplication;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.view.EditTextWithDel;
import org.litepal.crud.DataSupport;

/**
 * Created by charle-chen on 15/7/19.
 */
public class EditSignActivity extends BaseActivity {

    @InjectView(R.id.et_sign)
    EditTextWithDel etSign;
    private int type;
    private User user;

    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Intent intent = getIntent();
                    setResult(CommonConstant.GOTO_SET_FRAGMENT, intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_sign);
        ButterKnife.inject(this);
        afterView();
        initData();
    }

    protected void afterView() {
        ActionBar actionBar = getActionBar();
        actionBar.show();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initData() {
        super.initData();
        user = MainApplication.getUser();
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        if (type == CommonConstant.EDIT_SIGN) {
            etSign.setText(user.getSign());
        } else if (type == CommonConstant.EDIT_NICKNAME) {
            etSign.setText(user.getNickName());
        }
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                String string = etSign.getText().toString();
                final ContentValues contentValues = new ContentValues();
                if (type == CommonConstant.EDIT_SIGN) {
                    contentValues.put("sign", string);
                    MainApplication.getUser().setSign(string);
                }
                if (type == CommonConstant.EDIT_NICKNAME) {
                    contentValues.put("nickname", string);
                    MainApplication.getUser().setNickName(string);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        User user = DataSupport.findAll(User.class).get(0);
                        DataSupport.update(User.class, contentValues, user.getId());
                        mHandler.sendEmptyMessage(1);
                    }
                }).start();
                break;
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }
}
