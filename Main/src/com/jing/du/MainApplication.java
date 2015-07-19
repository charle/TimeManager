package com.jing.du;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.jing.du.Main.Model.User;
import com.jing.du.Main.R;
import com.jing.du.common.utils.Log;
import com.jing.du.common.utils.StringUtils;
import org.litepal.LitePalApplication;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;
import org.litepal.util.DBUtility;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by charle-chen on 15/6/21.
 */
public class MainApplication extends LitePalApplication {

    private static String DB_PATH = "/data/data/com.jing.du.Main/databases/";
    private static final String DB_NAME = "du_time.db";
    private Context context;
    public static boolean dataLoaded = false;
    public static User user = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        LitePalApplication.initialize(this);
        SQLiteDatabase db = Connector.getDatabase();
        initData(db);
        Log.isDebug = true;
    }

    private void initData(final SQLiteDatabase db) {

        if (DBUtility.isTableExists("tag", db) && DataSupport.count("tag") == 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    InputStream myInput = context.getResources().openRawResource(
                            R.raw.du_time);
                    InputStreamReader reader = new InputStreamReader(myInput);
                    BufferedReader breader = new BufferedReader(reader);

                    String outFileName = DB_PATH + DB_NAME, str;
                    try {
                        FileWriter myOutput = new FileWriter(outFileName, true);
                        while ((str = breader.readLine()) != null) {
                            db.execSQL(str);
                        }
                        myOutput.flush();
                        myOutput.close();
                        myInput.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        dataLoaded = true;
                    }

                    List<User> userList = DataSupport.findAll(User.class, true);
                    if (!StringUtils.isListEmpty(userList)) {
                        user = userList.get(0);
                    }
                }
            }).start();
        } else {
            dataLoaded = true;
        }

    }

    public static User getUser() {
        if (user == null) {
            List<User> userList = DataSupport.findAll(User.class, true);
            if (!StringUtils.isListEmpty(userList)) {
                user = userList.get(0);
            }
        }
        return user;
    }
}
