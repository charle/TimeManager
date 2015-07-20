package com.jing.du.Main.FragmentView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.jing.du.Main.Activity.ClipImageActivity;
import com.jing.du.Main.Activity.EditSignActivity;
import com.jing.du.Main.Activity.SetPasswordActivity;
import com.jing.du.Main.Model.Diary;
import com.jing.du.Main.Model.DiaryItem;
import com.jing.du.Main.Model.User;
import com.jing.du.Main.R;
import com.jing.du.MainApplication;
import com.jing.du.common.Interface.CommonInit;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.file.OutputDb;
import com.jing.du.common.utils.DateUtils;
import com.jing.du.common.utils.Log;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.utils.Toast;
import com.jing.du.common.view.SwitchView;
import org.litepal.crud.DataSupport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by charle-chen on 15/7/2.
 */
public class SettingFragment extends Fragment implements CommonInit {

    private View mainView;
    private Handler mHandler;
    private Context context;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHandler();
        initData();
        context = getActivity();
    }

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
        mainView = inflater.inflate(R.layout.setting, container, false);
        initViews();
        ButterKnife.inject(this, mainView);
        return mainView;
    }

    @Override
    public void initData() {
        user = MainApplication.getUser();
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
        ImageView ivProfile = (ImageView) mainView.findViewById(R.id.iv_profile);
        Bitmap bitmap = getLoacalBitmap(CommonConstant.PROFILE_PATH);
        if (!StringUtils.isObjectEmpty(bitmap)) {
            ivProfile.setImageBitmap(bitmap);
        }
        ivProfile.setOnClickListener(clickEvent);

        final TableRow signRow = (TableRow) mainView.findViewById(R.id.tr_sign);
        signRow.setOnClickListener(clickEvent);

        TextView nickName = (TextView) mainView.findViewById(R.id.tv_nickname);
        nickName.setOnClickListener(clickEvent);

        LinearLayout passwordRow = (LinearLayout) mainView.findViewById(R.id.tr_password);
        passwordRow.setOnClickListener(clickEvent);

        SwitchView lock_switch = (SwitchView) mainView.findViewById(R.id.lock_switch);
        final SharedPreferences sp = getActivity().getSharedPreferences("setting", 0);
        lock_switch.setSwitchStatus(sp.getBoolean("locked", false));
        lock_switch.setOnSwitchChangeListener(new SwitchView.OnSwitchChangeListener() {
            @Override
            public void onSwitchChanged(boolean open) {
                SharedPreferences.Editor editor = sp.edit();
                if (open) {
                    editor.putBoolean("locked", true);
                    editor.commit();
                } else {
                    editor.putBoolean("locked", false);
                    editor.commit();
                }
            }
        });
        mHandler.sendEmptyMessage(3);
    }

    @Override
    public void initHandler() {
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        Toast.show(getActivity(), "导出成功", 1000);
                        break;
                    case 2:
                        refreshView();
                        break;
                    case 3:
                        notifyDataSetChanged();
                        break;
                }
            }
        };
    }

    @Override
    public void notifyDataSetChanged() {
        if (!StringUtils.isObjectEmpty(user) && !StringUtils.isViewEmpty(mainView)) {
            TextView signTV = (TextView) mainView.findViewById(R.id.tv_sign);
            signTV.setText(user.getSign());
            TextView nickName = (TextView) mainView.findViewById(R.id.tv_nickname);
            nickName.setText(user.getNickName());
        }
    }

    @Override
    public void refreshView() {
        ImageView ivProfile = (ImageView) mainView.findViewById(R.id.iv_profile);
        Bitmap bitmap = getLoacalBitmap(CommonConstant.PROFILE_PATH);
        if (!StringUtils.isObjectEmpty(bitmap)) {
            ivProfile.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private View.OnClickListener clickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_profile:
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, CommonConstant.GOTO_PICK_IMAGE);
                    break;
                case R.id.tr_sign:
                    Intent intent1 = new Intent();
                    intent1.putExtra("type", CommonConstant.EDIT_SIGN);
                    intent1.setClass(getActivity(), EditSignActivity.class);
                    startActivityForResult(intent1, CommonConstant.GOTO_EDIT_SIGN);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case R.id.tv_nickname:
                    Intent intent2 = new Intent();
                    intent2.putExtra("type", CommonConstant.EDIT_NICKNAME);
                    intent2.setClass(getActivity(), EditSignActivity.class);
                    startActivityForResult(intent2, CommonConstant.GOTO_EDIT_NICKNAME);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case R.id.tr_password:
                    Intent intent3 = new Intent();
                    intent3.setClass(getActivity(), SetPasswordActivity.class);
                    startActivity(intent3);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CommonConstant.GOTO_PICK_IMAGE:
                if (resultCode == -1) {
                    Uri uri = data.getData();
                    Intent intent = new Intent();
                    intent.putExtra("uri", uri);
                    intent.setClass(getActivity(), ClipImageActivity.class);
                    startActivityForResult(intent, CommonConstant.GOTO_CLIP_IMAGE);
                }
                break;
            case CommonConstant.GOTO_CLIP_IMAGE:
                if (resultCode == CommonConstant.GOTO_SET_FRAGMENT) {
                    refreshView();
                }
                break;
            case CommonConstant.GOTO_EDIT_SIGN:
                notifyDataSetChanged();
                break;
            case CommonConstant.GOTO_EDIT_NICKNAME:
                notifyDataSetChanged();
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    private Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片
        } catch (FileNotFoundException e) {
            Log.d(e.getMessage());
            return null;
        }
    }
}
