package com.jing.du.Main.FragmentView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.jing.du.Main.Activity.CreateMinderActivity;
import com.jing.du.Main.Adapter.MinderAdapter;
import com.jing.du.Main.Model.Minder;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.MinderViewHolder;
import com.jing.du.common.Interface.CommonInit;
import com.jing.du.common.constant.CommonConstant;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by charle-chen on 15/7/12.
 */
public class MinderFragment extends ListFragment implements CommonInit {
    private View mainView;
    private List<Minder> list;
    private Handler mHandler;
    private MinderAdapter adapter;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.layout_to_do_list, container, false);
        initViews();
        mHandler.sendEmptyMessage(1);
        return mainView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        initData();
        initHandler();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                list = DataSupport.order("createtime desc").find(Minder.class,true);
                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }

    @Override
    public void initViews() {
        TextView textView = (TextView)mainView.findViewById(R.id.tv_create_new_minder);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), CreateMinderActivity.class);
                startActivityForResult(intent, CommonConstant.GOTO_CREATE_MINDER);
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }

    @Override
    public void initHandler() {
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        notifyDataSetChanged();
                        break;
                }
            }
        };
    }

    @Override
    public void notifyDataSetChanged() {
        if (adapter == null) {
            MinderViewHolder minderViewHolder = new MinderViewHolder();
            adapter = new MinderAdapter(context, list, minderViewHolder, R.layout.minder_item);
        }
        getListView().setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void refreshView() {

    }

}
