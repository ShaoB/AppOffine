package com.levcn.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.levcn.R;
import com.levcn.adapter.GeneralPagerAdapter;
import com.levcn.base.BaseActivity;
import com.levcn.eventbus.EventMessage;
import com.levcn.fragment.BacklogFragment;
import com.levcn.fragment.CompletedFragment;
import com.levcn.fragment.NoticeFragment;
import com.levcn.presenter.MainPresenter;
import com.levcn.util.SystemUi;
import com.levcn.view.IMainView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shaobin
 */
public class MainActivity extends BaseActivity<IMainView, MainPresenter> implements IMainView {

    private ViewPager mViewpager;
    private TabLayout mTabLayout;

    @Override
    protected void onMyCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initViews() {
        mViewpager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tab_layout);

        List<Fragment> tabFragments = getViews();
        GeneralPagerAdapter pagerAdapter = new GeneralPagerAdapter(this.getSupportFragmentManager(), tabFragments);
        mViewpager.setOffscreenPageLimit(tabFragments.size());
        this.mTabLayout.setupWithViewPager(mViewpager);
        mViewpager.setAdapter(pagerAdapter);
        mViewpager.setCurrentItem(0);
        this.setTabLayout();

        SystemUi.fixSystemUI(this, true);
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - this.exitTime) > 2000) {
                ToastUtils.showShort("再按一次退出程序");
                this.exitTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Override
    public void onReceiveEvent(EventMessage event) {

    }

    @SuppressLint("InflateParams")
    private List<Fragment> getViews() {
        List<Fragment> views = new ArrayList<>();
        views.add(new BacklogFragment());
        views.add(new CompletedFragment());
        views.add(new NoticeFragment());
        return views;
    }

    private void setTabLayout() {
        Tab[] tabs = new Tab[mTabLayout.getTabCount()];
        tabs[0] = new Tab(R.drawable.tab_backlog_selector, "待办任务");
        tabs[1] = new Tab(R.drawable.tab_completed_selector, "已办任务");
        tabs[2] = new Tab(R.drawable.tab_notice_selector, "通知");

        for (int i = 0; i < tabs.length; i++) {
            View view = this.buildView(tabs[i].getIcon(), tabs[i].getTag());
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(view);
            }
        }
    }

    private View buildView(int resId, String tabTxt) {
        @SuppressLint("InflateParams") TextView tabTextView = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_indicator, null);
        Drawable tabIcon = ContextCompat.getDrawable(this, resId);
        tabTextView.setCompoundDrawablesWithIntrinsicBounds(null, tabIcon, null, null);
        tabTextView.setText(tabTxt);
        return tabTextView;
    }

    static class Tab {
        private int icon;
        private String tag;

        private Tab(int icon, String tag) {
            this.icon = icon;
            this.tag = tag;
        }

        private int getIcon() {
            return icon;
        }

        public String getTag() {
            return tag;
        }
    }
}