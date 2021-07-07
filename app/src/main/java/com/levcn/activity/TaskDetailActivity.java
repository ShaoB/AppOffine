package com.levcn.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.levcn.R;
import com.levcn.adapter.ImageAdapter;
import com.levcn.base.BaseActivity;
import com.levcn.bean.ImageInfo;
import com.levcn.greendao.entiy.TaskEntity;
import com.levcn.greendao.utils.Constants;
import com.levcn.greendao.utils.JumpKey;
import com.levcn.greendao.utils.JumpUtils;
import com.levcn.presenter.MainPresenter;
import com.levcn.util.PictureFileUtil;
import com.levcn.view.IMainView;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shaobin
 */
public class TaskDetailActivity extends BaseActivity<IMainView, MainPresenter> implements IMainView {

    private TextView mTvWorkAddress, mTvWorkContent, mTvNearbyPeople, mTvReceiveTime, mTvTaskState, mTvInspectContent, mTvInspectTime;
    private Button mBtScan, mBtPush;
    private ImageView mIvBack;
    private int taskState;
    private TaskEntity taskEntity;
    private LinearLayout mLlAction, mLlReceiveTime, mLlTaskState, mLlInspectContent, mLlPic, mLlInspectTime;
    private RecyclerView mRvImg;

    private List<ImageInfo> imgUrls = new ArrayList<>();
    private ImageAdapter imageAdapter;

    @Override
    protected void onMyCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_task_detail);
    }

    @Override
    protected void initViews() {
        TextView mTvHeaderLayoutContent = findViewById(R.id.header_layout_content_tv);
        mTvHeaderLayoutContent.setText("任务详情");
        mIvBack = findViewById(R.id.iv_back);
        mIvBack.setVisibility(View.VISIBLE);
        mTvWorkAddress = findViewById(R.id.tv_work_address);
        mTvWorkContent = findViewById(R.id.tv_work_content);
        mTvNearbyPeople = findViewById(R.id.tv_nearby_people);
        mLlReceiveTime = findViewById(R.id.ll_receive_time);
        mTvReceiveTime = findViewById(R.id.tv_receive_time);
        mLlTaskState = findViewById(R.id.ll_task_state);
        mTvTaskState = findViewById(R.id.tv_task_state);
        mLlInspectContent = findViewById(R.id.ll_inspect_content);
        mTvInspectContent = findViewById(R.id.tv_inspect_content);
        mLlPic = findViewById(R.id.ll_pic);
        mRvImg = findViewById(R.id.rv_img);
        mLlInspectTime = findViewById(R.id.ll_inspect_time);
        mTvInspectTime = findViewById(R.id.tv_inspect_time);

        mLlAction = findViewById(R.id.ll_action);
        mBtScan = findViewById(R.id.bt_scan);
        mBtPush = findViewById(R.id.bt_push);

        addStatusBar(true);

        taskState = getIntent().getIntExtra(JumpKey.TASK_STATE, 0);
        taskEntity = getIntent().getParcelableExtra(JumpKey.TASK_DETAIL);

        updateView();
    }

    private void updateView() {
        if (taskState == Constants.TASK_STATE_NEED_DECISION) {
            mLlAction.setVisibility(View.VISIBLE);
            mLlReceiveTime.setVisibility(View.VISIBLE);
            mLlTaskState.setVisibility(View.VISIBLE);
            mLlInspectContent.setVisibility(View.GONE);
            mLlPic.setVisibility(View.GONE);
            mRvImg.setVisibility(View.GONE);
            mLlInspectTime.setVisibility(View.GONE);
        } else {
            mLlAction.setVisibility(View.GONE);
            mLlReceiveTime.setVisibility(View.GONE);
            mLlTaskState.setVisibility(View.GONE);
            mLlInspectContent.setVisibility(View.VISIBLE);
            mLlPic.setVisibility(View.VISIBLE);
            mRvImg.setVisibility(View.VISIBLE);
            mLlInspectTime.setVisibility(View.VISIBLE);

            imageAdapter = new ImageAdapter(imgUrls);
            imageAdapter.addChildClickViewIds(R.id.subject_sign_item_delete_iv);
            mRvImg.setLayoutManager(new GridLayoutManager(this, 3));
            mRvImg.setAdapter(imageAdapter);

        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        if (taskEntity != null) {
            mTvWorkAddress.setText(taskEntity.getF_GZDD_NAME());
            mTvWorkContent.setText(taskEntity.getF_GZNR());
            mTvNearbyPeople.setText(taskEntity.getF_PZR_NAME());
            mTvReceiveTime.setText("2021-01-02");
            mTvTaskState.setText("待见证");
            mTvInspectContent.setText(taskEntity.getInspectContent());
            if (!StringUtils.isEmpty(taskEntity.getImgUrls())) {
                String[] imags = taskEntity.getImgUrls().split(",");
                for (String s : imags) {
                    imgUrls.add(new ImageInfo(s, 1));
                }
                imageAdapter.notifyDataSetChanged();
            }
            mTvInspectTime.setText(taskEntity.getInspectTime());
        } else {
            ToastUtils.showShort("值传递错误");
        }
    }

    @Override
    protected void initListener() {
        mBtScan.setOnClickListener(new ClickListener());
        mBtPush.setOnClickListener(new ClickListener());
        mIvBack.setOnClickListener(new ClickListener());
        if (imageAdapter != null) {
            imageAdapter.setOnItemClickListener((adapter, view, position) -> {
                List<LocalMedia> selectList = new ArrayList<>();
                for (ImageInfo imageInfo : imgUrls) {
                    LocalMedia localMedia = new LocalMedia();
                    localMedia.setPath(imageInfo.getUrl());
                    selectList.add(localMedia);
                }
                PictureFileUtil.previewImage(TaskDetailActivity.this, position, selectList);
            });
        }
    }

    @Override
    protected void onDelayClick(View view) {
        if (view.getId() == R.id.bt_scan) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(JumpKey.TASK_DETAIL, taskEntity);
            JumpUtils.goNext(mActivity, CaptureMicaActivity.class, "bundle", bundle, true);
        } else if (view.getId() == R.id.bt_push) {
            ToastUtils.showShort("推送");
        } else if (view.getId() == R.id.iv_back) {
            finish();
        }
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }
}