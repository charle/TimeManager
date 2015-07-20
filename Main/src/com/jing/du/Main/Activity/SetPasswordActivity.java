package com.jing.du.Main.Activity;

import android.app.ActionBar;
import android.content.ContentValues;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.jing.du.Main.Model.User;
import com.jing.du.Main.R;
import com.jing.du.MainApplication;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.utils.Toast;
import com.jing.du.common.view.GestureLockViewGroup;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class SetPasswordActivity extends BaseActivity {

    @InjectView(R.id.gesture_lock)
    GestureLockViewGroup gestureLock;
    @InjectView(R.id.password_warn)
    TextView passwordWarn;

    private List<Integer> firstPassword = new ArrayList<Integer>();
    private List<Integer> secondPassword = new ArrayList<Integer>();
    private User user;
    private boolean isFirstSelect = true;
    private boolean changePassword = false;
    private int[] password;

    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.show(SetPasswordActivity.this, "设置密码成功", 1000);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_set_password);
        ButterKnife.inject(this);
        afterView();
        initData();
        initEvent();
    }

    private void afterView() {
        ActionBar actionBar = getActionBar();
        actionBar.show();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initData() {
        super.initData();
        user = MainApplication.getUser();
        if (!StringUtils.isObjectEmpty(user)) {
            changePassword = true;
            passwordWarn.setText("请输入原始密码");
        } else {
            passwordWarn.setText("请输入新密码");
        }
        convertPasswordToInteger();
    }

    @Override
    public void initEvent() {
        super.initEvent();
        gestureLock.setOnGestureLockViewListener(new GestureLockViewGroup.OnGestureLockViewListener() {
            @Override
            public void onBlockSelected(int cId) {
            }

            @Override
            public void onGestureEvent(boolean matched) {
                createOrUpdatePassword(matched);
            }

            @Override
            public void onUnmatchedExceedBoundary() {

            }
        });
    }

    private boolean justTwoArrayIsEqual(List<Integer> list1, List<Integer> list2) {
        int legth1 = list1.size();
        int legth2 = list2.size();
        int legth = 0;
        if (legth1 != legth2) {
            return false;
        } else {
            legth = legth1 > legth2 ? legth2 : legth1;
        }

        for (int i = 0; i < legth; i++) {
            if (list1.get(i) != list2.get(i)) {
                return false;
            }
        }
        return true;
    }

    private void convertPasswordToInteger() {
        if (changePassword) {
            String[] passwordStr = user.getPassword().split(",");
            password = new int[passwordStr.length];
            for (int i = 0; i < passwordStr.length; i++) {
                password[i] = Integer.parseInt(passwordStr[i]);
            }
            gestureLock.setAnswer(password);
        }
    }

    private void createOrUpdatePassword(boolean matched) {
        if (changePassword) {
            if (matched) {
                passwordWarn.setText("密码输入正确，请输入新密码");
                changePassword = false;
            } else {
                passwordWarn.setText("密码输入错误，请重新输入");
            }
        } else {
            if (isFirstSelect) {
                firstPassword = gestureLock.getChosse();
                isFirstSelect = false;
                passwordWarn.setText("请重新输入密码");
            } else {
                secondPassword = gestureLock.getChosse();
                isFirstSelect = true;
                if (justTwoArrayIsEqual(firstPassword, secondPassword)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String str = "";
                            for (int i = 0; i < firstPassword.size(); i++) {
                                str += firstPassword.get(i) + ",";
                            }
                            str = str.substring(0, str.length() - 1);
                            ContentValues values = new ContentValues();
                            values.put("password", str);
                            DataSupport.update(User.class, values, user.getId());
                            MainApplication.getUser().setPassword(str);
                            mHandler.sendEmptyMessage(1);
                        }
                    }).start();
                } else {
                    passwordWarn.setText("两次输入的密码不一致");
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }
}