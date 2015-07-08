package com.jing.du.Main.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewConfiguration;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.jing.du.Main.FragmentView.HomeFragment;
import com.jing.du.Main.FragmentView.SettingFragment;
import com.jing.du.Main.FragmentView.TagFragment;
import com.jing.du.Main.R;
import com.jing.du.common.adapter.MyFragmentPagerAdapter;
import com.jing.du.common.utils.Log;
import com.jing.du.common.view.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements
        ViewPager.OnPageChangeListener {

    private static final float HALF_ALPHA = 0.5f;
    private static final float FULL_ALPHA = 0.0f;
    private final Context context = this;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    @InjectView(R.id.indicator_home)
    ChangeColorIconWithText indicatorHome;
    @InjectView(R.id.indicator_list)
    ChangeColorIconWithText indicatorList;
    @InjectView(R.id.indicator_tag)
    ChangeColorIconWithText indicatorTag;
    @InjectView(R.id.indicator_setting)
    ChangeColorIconWithText indicatorSetting;
    private List<Fragment> mTabs = new ArrayList<Fragment>();
    private HomeFragment homeFragment;
    private TagFragment tagFragment;
    private SettingFragment settingFragment;

    private MyFragment  fragmentTwo;
    private MyFragmentPagerAdapter mAdapter;
    private List<ChangeColorIconWithText> mTabIndicators = new ArrayList<ChangeColorIconWithText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.inject(this);
        afterInitView();
        Log.d("activity is created");
    }



    private void afterInitView() {
        indicatorHome.setIconAlpha(HALF_ALPHA);
        initDatas();
        initEvent();
    }

    /**
     * 初始化所有事件
     */
    private void initEvent() {
        viewpager.setOnPageChangeListener(this);
    }

    private void initDatas() {
        setOverflowButtonAlways();
        homeFragment = new HomeFragment();
        fragmentTwo = MyFragment.newInstance(R.layout.list);
        tagFragment = new TagFragment();
        settingFragment = new SettingFragment();

        mTabIndicators.add(indicatorHome);
        mTabIndicators.add(indicatorList);
        mTabIndicators.add(indicatorTag);
        mTabIndicators.add(indicatorSetting);

        mTabs.add(homeFragment);
        mTabs.add(fragmentTwo);
        mTabs.add(tagFragment);
        mTabs.add(settingFragment);

        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mTabs);
        viewpager.setAdapter(mAdapter);
    }

    private void setOverflowButtonAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKey = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            menuKey.setAccessible(true);
            menuKey.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击Tab按钮
     *
     * @param v
     */
    @OnClick({R.id.indicator_home, R.id.indicator_list, R.id.indicator_tag, R.id.indicator_setting})
    public void clickTab(View v) {
        resetOtherTabs();
        switch (v.getId()) {
            case R.id.indicator_home:
                mTabIndicators.get(0).setIconAlpha(HALF_ALPHA);
                viewpager.setCurrentItem(0, false);
                Log.d("进入viewpager第一页");
                break;
            case R.id.indicator_list:
                mTabIndicators.get(1).setIconAlpha(HALF_ALPHA);
                viewpager.setCurrentItem(1, false);
                Log.d("进入viewpager第二页");
                break;
            case R.id.indicator_tag:
                mTabIndicators.get(2).setIconAlpha(HALF_ALPHA);
                viewpager.setCurrentItem(2, false);
                Log.d("进入viewpager第三页");
                break;
            case R.id.indicator_setting:
                mTabIndicators.get(3).setIconAlpha(HALF_ALPHA);
                viewpager.setCurrentItem(3, false);
                Log.d("进入viewpager第四页");
                break;
        }
    }

    /**
     * 重置其他的TabIndicator的颜色
     */
    private void resetOtherTabs() {
        for (int i = 0; i < mTabIndicators.size(); i++) {
            mTabIndicators.get(i).setIconAlpha(FULL_ALPHA);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        if (positionOffset > 0) {
            ChangeColorIconWithText left = mTabIndicators.get(position);
            ChangeColorIconWithText right = mTabIndicators.get(position + 1);
            left.setIconAlpha(HALF_ALPHA - positionOffset / 2);
            right.setIconAlpha(positionOffset / 2);
        }
    }

    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        switch (position) {
            case 0:
                mTabIndicators.get(0).setIconAlpha(HALF_ALPHA);
                viewpager.setCurrentItem(0, false);
                Log.d("进入viewpager第一页");
                break;
            case 1:
                mTabIndicators.get(1).setIconAlpha(HALF_ALPHA);
                viewpager.setCurrentItem(1, false);
                Log.d("进入viewpager第二页");
                break;
            case 2:
                mTabIndicators.get(2).setIconAlpha(HALF_ALPHA);
                viewpager.setCurrentItem(2, false);
                Log.d("进入viewpager第三页");
                break;
            case 3:
                mTabIndicators.get(3).setIconAlpha(HALF_ALPHA);
                viewpager.setCurrentItem(3, false);
                Log.d("进入viewpager第四页");
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // TODO Auto-generated method stub

    }
}
