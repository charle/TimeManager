package com.jing.du.Main.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.jing.du.common.utils.Toast;

/**
 * Created by charle-chen on 15/7/20.
 */
public class AlarmReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("msg");
        Toast.show(context,msg,1000);
    }
}
