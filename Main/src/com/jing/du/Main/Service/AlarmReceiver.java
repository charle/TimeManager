package com.jing.du.Main.Service;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import com.jing.du.Main.Activity.DialogActivity;
import com.jing.du.Main.Activity.DiaryDetailActivity;
import com.jing.du.Main.Model.Minder;
import com.jing.du.common.utils.Log;
import org.litepal.crud.DataSupport;

/**
 * Created by charle-chen on 15/7/20.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private Context context;
    private int mindId;
    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Intent intent = new Intent();
                    intent.setClass(context, DialogActivity.class);
                    intent.putExtra("minder_id",mindId);
                    //  要想在Service中启动Activity，必须设置如下标志
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    break;
            }
        }
    };

    public void onReceive(Context context, Intent intent) {
        mindId = intent.getIntExtra("minder_id", 1);
        this.context = context;
        Log.d("mind_id>>>>>>>>>" + mindId);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentValues values = new ContentValues();
                values.put("mindornotmind", 0);
                DataSupport.update(Minder.class, values, mindId);
                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }
}
