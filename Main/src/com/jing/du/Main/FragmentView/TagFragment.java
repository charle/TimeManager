package com.jing.du.Main.FragmentView;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.jing.du.Main.Adapter.CategoyAdapter;
import com.jing.du.Main.Model.Category;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.CategoyViewHolder;
import com.jing.du.common.Interface.CommonInit;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.utils.Toast;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by charle-chen on 15/6/29.
 */
public class TagFragment extends Fragment implements CommonInit {

    private List<Category> categoryList;
    private LinearLayout progressBar;
    private CategoyAdapter categoyAdapter;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (!StringUtils.isViewEmpty(progressBar)) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    break;
                case 2:
                    categoyAdapter.notifyDataSetChanged();
                    break;

            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.tag, container, false);
//        progressBar = (LinearLayout)mainView.findViewById(R.id.lv_progress_bar);
        final BootstrapEditText editText = (BootstrapEditText) mainView.findViewById(R.id.et_category);
        BootstrapButton button = (BootstrapButton) mainView.findViewById(R.id.bt_add_category);
        final ListView listView = (ListView) mainView.findViewById(R.id.lv_category);

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
                            category.save();//异步过程来处理
                            categoryList.add(category);
                            mHandler.sendEmptyMessage(2);
                        }
                    }).start();
                }
                editText.setText("");
            }
        });
        CategoyViewHolder viewHolder = new CategoyViewHolder();
        categoyAdapter = new CategoyAdapter(getActivity(), categoryList, viewHolder, R.layout.category_item);
        listView.setAdapter(categoyAdapter);
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
                categoryList = DataSupport.findAll(Category.class, true);
                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }
}
