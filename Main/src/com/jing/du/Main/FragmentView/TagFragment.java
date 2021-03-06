package com.jing.du.Main.FragmentView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.jing.du.Main.Activity.DetailCategoryActivity;
import com.jing.du.Main.Adapter.CategoyAdapter;
import com.jing.du.Main.Model.Category;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.CategoyViewHolder;
import com.jing.du.common.Interface.CommonInit;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.utils.Log;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.utils.Toast;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by charle-chen on 15/6/29.
 */
public class TagFragment extends Fragment implements CommonInit {

    private List<Category> categoryList;
    private CategoyAdapter categoyAdapter;
    private View mainView;
    private ListView listView;
    private Handler mHandler;

    AdapterView.OnItemClickListener listItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt("category_id", categoryList.get(position).getId());
            bundle.putInt("position", position);
            intent.putExtras(bundle);
            intent.setClass(getActivity(), DetailCategoryActivity.class);
            startActivityForResult(intent, CommonConstant.GOTO_CATEGORY_DETAIL);
            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.tag, container, false);
        initViews();
        return mainView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHandler();
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("tagfragment destory view");
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
                categoryList = DataSupport.findAll(Category.class);
                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }

    @Override
    public void initViews() {
        final BootstrapEditText editText = (BootstrapEditText) mainView.findViewById(R.id.et_category);
        BootstrapButton button = (BootstrapButton) mainView.findViewById(R.id.bt_add_category);
        listView = (ListView) mainView.findViewById(R.id.lv_category);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String categoryString = editText.getText().toString();

                if (StringUtils.isEmpty(categoryString)) {
                    Toast.show(getActivity(), getActivity().getString(R.string.empty_warning), 1000);
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Category category = new Category();
                            category.setName(categoryString);
                            category.setDefaultType(CommonConstant.CATEGORY_ANOTHER);
                            category.save();//异步过程来处理
                            categoryList.add(category);
                            mHandler.sendEmptyMessage(3);
                        }
                    }).start();
                }
                editText.setText("");
            }
        });
        listView.setOnItemClickListener(listItemClick);
        mHandler.sendEmptyMessage(2);
    }

    @Override
    public void initHandler() {
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        CategoyViewHolder viewHolder = new CategoyViewHolder();
                        categoyAdapter = new CategoyAdapter(getActivity(), categoryList, viewHolder, R.layout.category_item);
                        listView.setAdapter(categoyAdapter);
                        categoyAdapter.notifyDataSetChanged();
                    case 2:
                        notifyDataSetChanged();
                        break;
                }
            }
        };
    }

    @Override
    public void notifyDataSetChanged() {
        if (categoyAdapter == null) {
            CategoyViewHolder viewHolder = new CategoyViewHolder();
            categoyAdapter = new CategoyAdapter(getActivity(), categoryList, viewHolder, R.layout.category_item);
        }
        listView.setAdapter(categoyAdapter);
        categoyAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshView() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CommonConstant.GOTO_CATEGORY_DETAIL:
                if (resultCode == CommonConstant.GOTO_TAG_FRAGMENT) {
                    int position = data.getIntExtra("position", 0);
                    categoryList.remove(position);
                    categoyAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }
}
