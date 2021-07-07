package com.levcn.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.levcn.R;
import com.levcn.adapter.MyFragmentAdapter;
import com.levcn.base.BaseLazyFragment;
import com.levcn.eventbus.EventCode;
import com.levcn.eventbus.EventMessage;

/**
 * @author : shaoBin
 * date   : 2021/7/5 17:51
 * desc   : 待办任务
 */
public class BacklogFragment extends BaseLazyFragment {

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private TabLayoutMediator mediator;
    private TextView mTvNeedTabRed, mTvUpdateTabRed;


    private String[] mTabs = {"待见证", "待上传"};
    private NeedUploadFragment needUploadFragment;
    private NeedDecisionFragment needDecisionFragment;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_backlog_layout;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews(View view) {

        mTabLayout = view.findViewById(R.id.tab_layout);
        mViewPager = view.findViewById(R.id.view_pager);

        mViewPager.setOffscreenPageLimit(2);
        //禁止滑动
        //mViewPager.setUserInputEnabled(false);
        MyFragmentAdapter myFragmentAdapter = new MyFragmentAdapter(mActivity);
        needDecisionFragment = new NeedDecisionFragment();
        myFragmentAdapter.addFragment(needDecisionFragment);
        needUploadFragment = new NeedUploadFragment();
        myFragmentAdapter.addFragment(needUploadFragment);
        mViewPager.setAdapter(myFragmentAdapter);

        mediator = new TabLayoutMediator(mTabLayout, mViewPager, true, new TabLayoutMediator.TabConfigurationStrategy() {

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //这里需要根据position修改tab的样式和文字等
                tab.setCustomView(R.layout.tab_hint_layout);
                View customView = tab.getCustomView();
                if (customView != null) {
                    TextView mTvTabTitle = customView.findViewById(R.id.tv_tab_title);
                    mTvTabTitle.setText(mTabs[position]);
                    if (position == 0) {
                        mTvNeedTabRed = customView.findViewById(R.id.tv_tab_red);
                    } else if (position == 1) {
                        mTvUpdateTabRed = customView.findViewById(R.id.tv_tab_red);
                    }
                } else {
                    tab.setText(mTabs[position]);
                }

            }
        });
        mediator.attach();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onReceiveEvent(EventMessage event) {
        if (event.getCode() == EventCode.NEED_DECISION_COUNT) {
            if ((Integer) event.getData() > 0) {
                mTvNeedTabRed.setVisibility(View.VISIBLE);
                mTvNeedTabRed.setText(event.getData() + "");
            } else {
                mTvNeedTabRed.setVisibility(View.GONE);
            }
        } else if (event.getCode() == EventCode.NEED_UPDATE_COUNT) {
            if ((Integer) event.getData() > 0) {
                mTvUpdateTabRed.setVisibility(View.VISIBLE);
                mTvUpdateTabRed.setText(event.getData() + "");
            } else {
                mTvUpdateTabRed.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onDestroy() {
        mediator.detach();
        mViewPager.unregisterOnPageChangeCallback(changeCallback);
        super.onDestroy();
    }

    private ViewPager2.OnPageChangeCallback changeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            //当滚动当前页面时，将调用此方法
            LogUtils.eTag("tab", "滚动当前页面：" + position);
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
        }
    };
}
