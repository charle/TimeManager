package com.jing.du.Main.Activity;

import android.app.Activity;
import android.view.KeyEvent;
import com.jing.du.Main.R;

/**
 * Created by charle-chen on 15/6/30.
 */
public class BaseActivity extends Activity{

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void initData(){

    }

    public void initEvent(){

    }
}

