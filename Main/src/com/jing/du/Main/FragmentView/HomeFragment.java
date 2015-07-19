package com.jing.du.Main.FragmentView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.jing.du.Main.Activity.CreateDiaryActivity;
import com.jing.du.Main.Activity.DiaryDetailActivity;
import com.jing.du.Main.Adapter.DiaryAdapter;
import com.jing.du.Main.Model.Diary;
import com.jing.du.Main.Model.User;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.DiaryViewHolder;
import com.jing.du.MainApplication;
import com.jing.du.common.Interface.CommonInit;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.utils.Log;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.view.XListView;
import org.litepal.crud.DataSupport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by charle-chen on 15/6/29.
 */
public class HomeFragment extends Fragment implements CommonInit {
    private DiaryAdapter diaryAdapter;
    private List<Diary> diaryList = new ArrayList<Diary>();
    private Context context;
    private View mainView;
    private XListView mListView;
    private View addHeaderView;
    private Handler mHandler;
    private int offset = 0;
    private static final int INCREASEMENT = 200;
    private User user;

    AdapterView.OnItemClickListener listItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt("diary_id", diaryList.get(position - 2).getId());
            bundle.putInt("list_position", position - 2);
            intent.putExtras(bundle);
            intent.setClass(getActivity(), DiaryDetailActivity.class);
            startActivityForResult(intent, CommonConstant.GOTO_DIARY_DETAIL);
            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHandler();
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.home, container, false);
        addHeaderView = inflater.inflate(R.layout.home_list_header, null);
        initViews();
        return mainView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("home fragment on attach");
        this.context = activity;
    }

    @Override
    public void initViews() {
        Log.d("initviews homefragment beginning");
        mListView = (XListView) mainView.findViewById(R.id.ll_main_listView);
        ImageView ivProfile = (ImageView) addHeaderView.findViewById(R.id.iv_profile);
        Bitmap bitmap = getLoacalBitmap(CommonConstant.PROFILE_PATH);
        if (!StringUtils.isObjectEmpty(bitmap)) {
            ivProfile.setImageBitmap(bitmap);
        }
        mListView.addHeaderView(addHeaderView);
        final ProgressBar pb_head = (ProgressBar) mainView.findViewById(R.id.pb_head);
        LinearLayout linearLayout = (LinearLayout) addHeaderView.findViewById(R.id.lv_create_new_diary);
        ImageView mReplaceBackground = (ImageView) addHeaderView.findViewById(R.id.iv_bg);
        XListView.IXListViewListener listViewListener = new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                pb_head.setVisibility(View.VISIBLE);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pb_head.setVisibility(View.GONE);
                        onLoad();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
//                loadMore();
            }

            private void onLoad() {
                mListView.stopRefresh();
                mListView.stopLoadMore();
            }
        };
        mReplaceBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity().getApplicationContext(), CreateDiaryActivity.class);
                startActivityForResult(intent, CommonConstant.GOTO_CREATE_DIARY);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        mListView.setXListViewListener(listViewListener);
        mListView.setOnItemClickListener(listItemClick);
        initHeaderView();
        mHandler.sendEmptyMessage(2);
    }

    @Override
    public void initHandler() {
        // TODO Auto-generated method stub
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        DiaryViewHolder diaryViewHolder = new DiaryViewHolder();
                        diaryAdapter = new DiaryAdapter(context, diaryList, diaryViewHolder, R.layout.home_list_main_item);
                        mListView.setAdapter(diaryAdapter);
                        diaryAdapter.notifyDataSetChanged();
                        initHeaderView();
                        break;
                    case 2:
                        notifyDataSetChanged();
                        break;
                }
            }
        };
    }

    @Override
    public void notifyDataSetChanged() {
        if (diaryAdapter == null) {
            DiaryViewHolder diaryViewHolder = new DiaryViewHolder();
            diaryAdapter = new DiaryAdapter(context, diaryList, diaryViewHolder, R.layout.home_list_main_item);
        }
        mListView.setAdapter(diaryAdapter);
        diaryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("homefragment destory view");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void initData() {
        user = MainApplication.getUser();
        new Thread(new Runnable() {
            @Override
            public void run() {
                diaryList = DataSupport.order("createtime desc").find(Diary.class, true);
                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }

    private void loadMore() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                diaryList.addAll(DataSupport.order("createtime desc").limit(INCREASEMENT).offset(offset).find(Diary.class, true));
                offset += INCREASEMENT;
                Log.d("offset is >>>>>>" + offset);
                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }

    //用于返回刷新
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CommonConstant.GOTO_DIARY_DETAIL:
                if (resultCode == CommonConstant.GOTO_HOME_FLAGMENT) {
                    int listPosition = data.getIntExtra("list_position", 0);
                    diaryList.remove(listPosition);
                    diaryAdapter.notifyDataSetChanged();
                }
                if (resultCode == CommonConstant.GOTO_HOME_FLAGMENT_FROM_EDIT_DIARY) {
                    int listPosition = data.getIntExtra("list_position", 0);
                    Diary diary = (Diary) data.getSerializableExtra("diary");
                    diaryList.set(listPosition, diary);
                    notifyDataSetChanged();
                }
                break;
            case CommonConstant.GOTO_CREATE_DIARY:
                if (resultCode == CommonConstant.GOTO_HOME_FLAGMENT) {
                    Diary tempDiary = (Diary) data.getSerializableExtra("diary");
                    diaryList.add(0, tempDiary);
                    notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void refreshView() {
        notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("home fragment is on resume");
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

    private void initHeaderView() {

        if (!StringUtils.isObjectEmpty(user)&&!StringUtils.isViewEmpty(addHeaderView)) {
            TextView signTv = (TextView) addHeaderView.findViewById(R.id.tv_sentence);
            TextView nickNameTv = (TextView) addHeaderView.findViewById(R.id.tv_name);
            signTv.setText(user.getSign());
            nickNameTv.setText(user.getNickName());
        }
    }
}
