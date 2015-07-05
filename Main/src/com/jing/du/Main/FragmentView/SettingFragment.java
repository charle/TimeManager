package com.jing.du.Main.FragmentView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.jing.du.Main.Model.Diary;
import com.jing.du.Main.Model.DiaryItem;
import com.jing.du.Main.R;
import com.jing.du.common.Interface.CommonInit;
import com.jing.du.common.file.TemplateEngine;
import com.jing.du.common.utils.DateUtils;
import com.jing.du.common.utils.Toast;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by charle-chen on 15/7/2.
 */
public class SettingFragment extends Fragment implements CommonInit {

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.show(getActivity(), "导出成功", 1000);
                    break;
            }
        }
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View mainView = inflater.inflate(R.layout.setting, container, false);
        Button button = (Button) mainView.findViewById(R.id.bt_output);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<Diary> diaryList = DataSupport.findAll(Diary.class, true);
                        List<HashMap<String,Object>> list = new ArrayList<HashMap<String, Object>>();
                        for (Diary diary : diaryList) {
                            HashMap<String, Object> data = new HashMap<String, Object>();
                            data.put("diary_time", "### "+DateUtils.getStringOfDate(diary.getCreateTime())
                            + " 晴 "+"上海市");
                            StringBuffer buffer = new StringBuffer();
                            for (DiaryItem item : diary.getDiaryItemArrayList()) {
                                DiaryItem diaryItem = DataSupport.find(DiaryItem.class, item.getId(), true);
                                buffer.append("- ");
                                buffer.append(diaryItem.getCategory().getName());
                                buffer.append(":");
                                buffer.append(diaryItem.getTag().getName());
                                buffer.append(" ");
                                buffer.append(diaryItem.getBeginTime());
                                buffer.append("~");
                                buffer.append(diaryItem.getEndTime());
                                buffer.append(" ");
                                buffer.append(diaryItem.getNote());
                                buffer.append("\n");
                            }
                            data.put("diary_items",buffer.toString());
                            list.add(data);
                        }
                        writeData("diary.tpl", "diary.md", getActivity(), list);
                        mHandler.sendEmptyMessage(1);

                    }
                }).start();
            }
        });
        return mainView;
    }

    @Override
    public void initData() {

    }

    private void writeData(String confPath, String filePath, Context context, List<HashMap<String, Object>> data) {
        TemplateEngine templateEngine = new TemplateEngine(confPath, context);
        // 设置换行符
//        templateEngine.setEnter(System.getProperty("line.separator"));
        // 读取模板文件
//        String template = templateEngine.readTemplate(confPath);
        // 替换模板变量
//        String dataString = templateEngine.replaceArgs(template, data);
        for(HashMap<String,Object> item : data){
            templateEngine.writeConf(filePath,item.get("diary_time").toString()+"\n"+item.get("diary_items").toString()+"\n",false);
        }
    }

}
