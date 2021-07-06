package com.levcn.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.levcn.R;
import com.levcn.base.BaseActivity;
import com.levcn.greendao.utils.JumpUtils;
import com.levcn.presenter.MainPresenter;
import com.levcn.util.SystemUi;
import com.levcn.view.IMainView;

/**
 * @author shaobin
 */
public class TaskDetailActivity extends BaseActivity<IMainView, MainPresenter> implements IMainView {

    private TextView mTvWorkAddress, mTvWorkContent, mTvNearbyPeople, mTvReceiveTime, mTvTaskState;
    private Button mBtScan, mBtPush;

    @Override
    protected void onMyCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_task_detail);
    }

    @Override
    protected void initViews() {
        TextView mTvHeaderLayoutContent = findViewById(R.id.header_layout_content_tv);
        mTvHeaderLayoutContent.setText("任务详情");
        mTvWorkAddress = findViewById(R.id.tv_work_address);
        mTvWorkContent = findViewById(R.id.tv_work_content);
        mTvNearbyPeople = findViewById(R.id.tv_nearby_people);
        mTvReceiveTime = findViewById(R.id.tv_receive_time);
        mTvTaskState = findViewById(R.id.tv_task_state);
        mBtScan = findViewById(R.id.bt_scan);
        mBtPush = findViewById(R.id.bt_push);

        addStatusBar(true);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        mTvWorkAddress.setText("汽轮基站");
        mTvWorkContent.setText("汽轮机检修，重大风险评估，确保安全。看看记录来看机会看看看见的莫高窟");
        mTvNearbyPeople.setText("梁先生，程先生");
        mTvReceiveTime.setText("2021-01-02");
        mTvTaskState.setText("待见证");
    }

    @Override
    protected void initListener() {
        mBtScan.setOnClickListener(new ClickListener());
        mBtPush.setOnClickListener(new ClickListener());
    }

    @Override
    protected void onDelayClick(View view) {
        if (view.getId() == R.id.bt_scan) {
            JumpUtils.goNext(mActivity, CaptureMicaActivity.class);
        } else if (view.getId() == R.id.bt_push) {
            ToastUtils.showShort("推送");
        }
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }
}