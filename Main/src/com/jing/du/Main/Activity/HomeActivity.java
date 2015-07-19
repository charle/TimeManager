package com.jing.du.Main.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import com.jing.du.Main.Model.User;
import com.jing.du.Main.R;
import com.jing.du.MainApplication;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.view.GestureLockViewGroup;

public class HomeActivity extends BaseActivity {

    private TextView passwordWarn;
    private GestureLockViewGroup gestureLock;
    private User user;
    private int[] password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = MainApplication.getUser();
        if (!StringUtils.isObjectEmpty(user)) {
            setContentView(R.layout.layout_set_password);
            afterView();
            convertPasswordToInteger();
            initEvent();
        } else {
            Intent intent = new Intent();
            intent.setClass(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void afterView() {
        passwordWarn = (TextView)findViewById(R.id.password_warn);
        gestureLock = (GestureLockViewGroup)findViewById(R.id.gesture_lock);
        passwordWarn.setText("请输入密码");
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initEvent() {
        super.initEvent();
        gestureLock.setOnGestureLockViewListener(
                new GestureLockViewGroup.OnGestureLockViewListener() {
                    @Override
                    public void onBlockSelected(int cId) {

                    }

                    @Override
                    public void onGestureEvent(boolean matched) {
                        if (!matched) {
                            passwordWarn.setText("输入密码错误，请重新输入");
                        } else {
                            Intent intent = new Intent();
                            intent.setClass(HomeActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    }

                    @Override
                    public void onUnmatchedExceedBoundary() {

                    }
                }

        );
    }

    private void convertPasswordToInteger() {
        String[] passwordStr = user.getPassword().split(",");
        password = new int[passwordStr.length];
        for (int i = 0; i < passwordStr.length; i++) {
            password[i] = Integer.parseInt(passwordStr[i]);
        }
        gestureLock.setAnswer(password);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dialog();
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认退出应用吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}