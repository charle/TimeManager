package com.jing.du.common.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jing.du.Main.R;
import com.jing.du.common.utils.Log;

/**
 * Created by charle-chen on 15/6/23.
 */
public class MyFragment extends Fragment {

    private View mainView;
    private int layoutId;

    public static final MyFragment newInstance(int layoutId) {
        MyFragment f = new MyFragment();
        Bundle bundle = new Bundle(2);
        bundle.putInt("layoutId", layoutId);
        f.setArguments(bundle);
        f.layoutId=layoutId;
        Log.d("create myfragment instance");
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("myFragment is created");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("myFragment destory view");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(this.layoutId, container, false);
        switch (this.layoutId){
            case R.layout.main:
                Log.d("main view on create...");
                break;
            case R.layout.list:
                Log.d("list view on create...");
                break;
            case R.layout.tag:
                Log.d("tag view on create...");
                break;
            case R.layout.setting:
                Log.d("setting view on create ...");
                break;
        }
        return mainView;
    }

    public View getFragmentView(){
        return this.mainView;
    }
}
