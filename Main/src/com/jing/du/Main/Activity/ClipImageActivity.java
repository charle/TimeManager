package com.jing.du.Main.Activity;

import android.app.ActionBar;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.jing.du.Main.R;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.utils.Log;
import com.jing.du.common.view.ClipImageLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by charle-chen on 15/7/18.
 */
public class ClipImageActivity extends BaseActivity {

    @InjectView(R.id.id_clipImageLayout)
    ClipImageLayout idClipImageLayout;

    private Uri uri;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Intent intent = getIntent();
                    ClipImageActivity.this.setResult(CommonConstant.GOTO_SET_FRAGMENT, intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_clip_image);
        ButterKnife.inject(this);
        afterView();
        initData();
    }

    private void afterView() {
        uri = getIntent().getParcelableExtra("uri");
        ContentResolver cr = getContentResolver();
        Drawable drawable = null;
        try {
            drawable = Drawable.createFromStream(cr.openInputStream(uri), null);
        } catch (Exception e) {
            Log.d(e.getMessage());
        }
        idClipImageLayout.setDrawable(drawable);
        ActionBar actionBar = getActionBar();
        actionBar.show();
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File f = new File(CommonConstant.PROFILE_PATH);
                        f.mkdirs();
                        if (f.exists()) {
                            f.delete();
                        }
                        try {
                            FileOutputStream out = new FileOutputStream(f);
                            Bitmap bm = idClipImageLayout.clip();
                            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
                            out.flush();
                            out.close();
                        } catch (FileNotFoundException e) {
                            Log.d(e.getMessage());
                        } catch (IOException e) {
                            Log.d(e.getMessage());
                        }
                        mHandler.sendEmptyMessage(1);
                    }
                }).start();
                break;
            default:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_tag, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
