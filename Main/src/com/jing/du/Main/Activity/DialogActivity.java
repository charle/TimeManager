package com.jing.du.Main.Activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.jing.du.Main.Model.Minder;
import com.jing.du.Main.R;
import com.jing.du.common.utils.StringUtils;
import org.litepal.crud.DataSupport;

public class DialogActivity extends BaseActivity {

    @InjectView(R.id.tv_minder_title)
    TextView tvMinderTitle;
    @InjectView(R.id.tv_minder_content)
    TextView tvMinderContent;
    @InjectView(R.id.bt_ok)
    BootstrapButton btOk;

    private int minderId;
    private Minder minder;
    private MediaPlayer mMediaPlayer;

    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    afterView();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_minder_dialog);
        ButterKnife.inject(this);
        initData();
    }

    private void afterView() {
        if (!StringUtils.isObjectEmpty(minder)) {
            tvMinderTitle.setText(minder.getTitle());
            tvMinderContent.setText(minder.getContent());
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer = MediaPlayer.create(this,R.raw.time_limit);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.start();
        }
    }

    @Override
    public void initData() {
        super.initData();
        minderId = getIntent().getIntExtra("minder_id", 1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                minder = DataSupport.find(Minder.class, minderId);
                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @OnClick({R.id.bt_ok})
    public void clickEvent(View v) {
        switch (v.getId()) {
            case R.id.bt_ok:
                Intent intent = new Intent();
                intent.setClass(DialogActivity.this, DetailMinderActivity.class);
                intent.putExtra("minder_id", minderId);
                startActivity(intent);
                finish();
                break;

        }
    }

    @Override
    protected void onDestroy() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        super.onDestroy();
    }
}