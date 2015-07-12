package com.jing.du.Main.FragmentView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import com.jing.du.Main.Model.Diary;
import com.jing.du.Main.Model.DiaryItem;
import com.jing.du.Main.R;
import com.jing.du.common.Interface.CommonInit;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.file.OutputDb;
import com.jing.du.common.utils.DateUtils;
import com.jing.du.common.utils.Toast;
import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;

/**
 * Created by charle-chen on 15/7/2.
 */
public class SettingFragment extends Fragment implements CommonInit {

    private View mainView;
    private Handler mHandler;
    private Context context;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initHandler();
        initData();
        context = getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.setting, container, false);
        initViews();
        return mainView;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViews() {
        TableRow output = (TableRow) mainView.findViewById(R.id.tr_output);
        output.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<Diary> diaryList = DataSupport.findAll(Diary.class, true);
                        OutputDb db = new OutputDb();
                        StringBuffer stringBuffer = new StringBuffer();
                        for (Diary diary : diaryList) {
                            HashMap<String, String> data = new HashMap<String, String>();
                            data.put("createt_time", DateUtils.getStringOfDate(diary.getCreateTime()));
                            data.put("weather", CommonConstant.WEATHER_STATE[diary.getWeatherType()]);
                            data.put("address", diary.getAddress());
                            stringBuffer.append(db.replace("### ${createt_time} ${weather} ${address}", data, true));
                            for (DiaryItem diaryItem : diary.getDiaryItemArrayList()) {
                                data.clear();
                                DiaryItem diaryItem1 = DataSupport.find(DiaryItem.class, diaryItem.getId(), true);
                                data.put("category_name", diaryItem1.getCategory().getName());
                                data.put("tag_name", diaryItem1.getTag().getName());
                                data.put("begin_time", diaryItem1.getBeginTime());
                                data.put("end_time", diaryItem1.getEndTime());
                                data.put("note", diaryItem1.getNote());
                                stringBuffer.append(db.replace("- ${category_name} ${tag_name} ${begin_time} ~ ${end_time} ${note}", data, true));
                            }
                            stringBuffer.append(System.getProperty("line.separator"));
                        }
                        db.writeDocument("diary.md", stringBuffer.toString(), false);
                        mHandler.sendEmptyMessage(1);

                    }
                }).start();
            }
        });

        TableRow evernoteRow = (TableRow) mainView.findViewById(R.id.tr_sign);
        evernoteRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                EvernoteSession.getInstance().authenticate(getActivity());
            }
        });
    }

    @Override
    public void initHandler() {
        mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1:
                        Toast.show(getActivity(), "导出成功", 1000);
                        break;
                }
            }
        };
    }

    @Override
    public void notifyDataSetChanged() {

    }

    @Override
    public void refreshView() {

    }
}
