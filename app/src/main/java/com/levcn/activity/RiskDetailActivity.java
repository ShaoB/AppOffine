package com.levcn.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.levcn.R;
import com.levcn.adapter.ImageAdapter;
import com.levcn.base.BaseActivity;
import com.levcn.bean.ImageInfo;
import com.levcn.greendao.entiy.RiskEntity;
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
public class RiskDetailActivity extends BaseActivity<IMainView, MainPresenter> implements IMainView {

    private TextView mTvRiskRank, mTvRiskDescribe, mTvRiskTime, mTvRiskDiscoverer;
    private RecyclerView mRvImg;
    private ImageView mIvBack;

    private List<ImageInfo> imgUrls = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private RiskEntity riskEntity;

    @Override
    protected void onMyCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_risk_detail);
    }

    @Override
    protected void initViews() {
        TextView mTvHeaderLayoutContent = findViewById(R.id.header_layout_content_tv);
        mTvHeaderLayoutContent.setText("隐患详情");
        mIvBack = findViewById(R.id.iv_back);
        mIvBack.setVisibility(View.VISIBLE);
        mTvRiskRank = findViewById(R.id.tv_risk_rank);
        mTvRiskDescribe = findViewById(R.id.tv_risk_describe);
        mRvImg = findViewById(R.id.rv_img);
        mTvRiskTime = findViewById(R.id.tv_risk_time);
        mTvRiskDiscoverer = findViewById(R.id.tv_risk_discoverer);

        imageAdapter = new ImageAdapter(imgUrls);
        imageAdapter.addChildClickViewIds(R.id.subject_sign_item_delete_iv);
        mRvImg.setLayoutManager(new GridLayoutManager(this, 3));
        mRvImg.setAdapter(imageAdapter);

        addStatusBar(true);
    }

    @Override
    protected void initData() {
        riskEntity = getIntent().getParcelableExtra(JumpKey.RISK_DETAIL);
        if (riskEntity != null) {
            mTvRiskRank.setText(riskEntity.getRiskRank());
            mTvRiskDescribe.setText(riskEntity.getRiskDescribe());
            mTvRiskTime.setText(riskEntity.getRiskTime());
            mTvRiskDiscoverer.setText(riskEntity.getDiscoverer());
            if (!StringUtils.isEmpty(riskEntity.getRiskImg())) {
                String[] imags = riskEntity.getRiskImg().split(",");
                for (String s : imags) {
                    imgUrls.add(new ImageInfo(s, 1));
                }
                imageAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initListener() {
        mIvBack.setOnClickListener(new ClickListener());
        imageAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<LocalMedia> selectList = new ArrayList<>();
            for (ImageInfo imageInfo : imgUrls) {
                LocalMedia localMedia = new LocalMedia();
                localMedia.setPath(imageInfo.getUrl());
                selectList.add(localMedia);
            }
            PictureFileUtil.previewImage(RiskDetailActivity.this, position, selectList);
        });
    }

    @Override
    protected void onDelayClick(View view) {
        if (view.getId() == R.id.iv_back) {
            finish();
        }
    }
}