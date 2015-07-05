package com.jing.du.Main.FragmentView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.jing.du.Main.Activity.CreateDiaryActivity;
import com.jing.du.Main.Activity.DiaryDetailActivity;
import com.jing.du.Main.Adapter.DiaryAdapter;
import com.jing.du.Main.Model.Diary;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.DiaryViewHolder;
import com.jing.du.common.Interface.CommonInit;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.view.XListView;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charle-chen on 15/6/29.
 */
public class HomeFragment extends Fragment implements CommonInit {

    AdapterView.OnItemClickListener listItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt("diary_id", diaryList.get(position-2).getId());
            intent.putExtras(bundle);
            intent.setClass(getActivity(), DiaryDetailActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    };
    AdapterView.OnItemLongClickListener listItemLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            return false;
        }
    };
    private List<Diary> diaryList = new ArrayList<Diary>();
    private LinearLayout progressBar;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (!StringUtils.isViewEmpty(progressBar)) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Handler mHandler = new Handler();
        View mainView = inflater.inflate(R.layout.home, container, false);
//        progressBar = (LinearLayout)mainView.findViewById(R.id.lv_progress_bar);
        final XListView mListView = (XListView) mainView.findViewById(R.id.ll_main_listView);
        final ProgressBar pb_head = (ProgressBar) mainView.findViewById(R.id.pb_head);
        View addHeaderView = inflater.inflate(R.layout.home_list_header, null);
        LinearLayout linearLayout = (LinearLayout) addHeaderView.findViewById(R.id.lv_create_new_diary);
        ImageView mReplaceBackground = (ImageView) addHeaderView.findViewById(R.id.iv_bg);
        DiaryViewHolder diaryViewHolder = new DiaryViewHolder();
        mListView.setAdapter(new DiaryAdapter(getActivity(), diaryList, diaryViewHolder, R.layout.home_list_main_item));
        mListView.addHeaderView(addHeaderView);
        mListView.setOnItemClickListener(listItemClick);
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
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        mListView.setXListViewListener(listViewListener);
        mListView.setOnItemClickListener(listItemClick);
        mListView.setOnItemLongClickListener(listItemLongClick);

        return mainView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                diaryList = DataSupport.order("createtime desc").find(Diary.class, true);
                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }

}
