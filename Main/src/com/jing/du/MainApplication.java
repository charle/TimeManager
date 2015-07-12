package com.jing.du;

import android.database.sqlite.SQLiteDatabase;
import com.jing.du.common.utils.Log;
import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

/**
 * Created by charle-chen on 15/6/21.
 */
public class MainApplication extends LitePalApplication {
    private static final String CONSUMER_KEY = "charle";
    private static final String CONSUMER_SECRET = "3b93d20b4656afc6";

    @Override
    public void onCreate() {
        super.onCreate();
        LitePalApplication.initialize(this);
        SQLiteDatabase db = Connector.getDatabase();
        Log.isDebug = true;
    }
}
