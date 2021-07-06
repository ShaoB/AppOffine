package com.levcn.fragment;

import android.view.View;

import com.levcn.R;
import com.levcn.base.BaseLazyFragment;

/**
 * @author : shaoBin
 * date   : 2021/7/5 17:51
 * desc   : 已办任务
 */
public class CompletedFragment extends BaseLazyFragment {
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_completed_layout;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews(View view) {
        addStatusBar(view);
    }
}
