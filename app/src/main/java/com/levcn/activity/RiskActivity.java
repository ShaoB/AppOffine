package com.levcn.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.levcn.R;
import com.levcn.adapter.ImageAdapter;
import com.levcn.base.BaseActivity;
import com.levcn.bean.ImageInfo;
import com.levcn.eventbus.EventBusUtils;
import com.levcn.eventbus.EventCode;
import com.levcn.eventbus.EventMessage;
import com.levcn.greendao.entiy.RiskEntity;
import com.levcn.greendao.entiy.TaskEntity;
import com.levcn.greendao.utils.CommonDaoUtils;
import com.levcn.greendao.utils.Constants;
import com.levcn.greendao.utils.DaoUtilsStore;
import com.levcn.presenter.MainPresenter;
import com.levcn.util.Base64Utils;
import com.levcn.util.PictureFileUtil;
import com.levcn.view.IMainView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author shaobin
 */
public class RiskActivity extends BaseActivity<IMainView, MainPresenter> implements IMainView {

    private EditText mEtRiskDescribe, mEtRiskDiscoverer, mEtRiskRank;
    private RecyclerView mRvImg;
    private TextView mTvRiskTime;
    private ImageView mIvBack;
    private Button mBtSave;

    /**
     * 已选图片
     */
    private List<LocalMedia> selectedImgs = new ArrayList<>();
    /**
     * 图片
     */
    private List<ImageInfo> imgUrls = new LinkedList<>();
    private ImageAdapter imageAdapter;

    @Override
    protected void onMyCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_risk);
    }

    @Override
    protected void initViews() {
        TextView mTvHeaderLayoutContent = findViewById(R.id.header_layout_content_tv);
        mTvHeaderLayoutContent.setText("发现隐患");
        mIvBack = findViewById(R.id.iv_back);
        mIvBack.setVisibility(View.VISIBLE);
        mEtRiskRank = findViewById(R.id.et_risk_rank);
        mEtRiskDescribe = findViewById(R.id.et_risk_describe);
        mRvImg = findViewById(R.id.rv_img);
        mTvRiskTime = findViewById(R.id.tv_risk_time);
        mTvRiskTime.setText(TimeUtils.date2String(TimeUtils.getNowDate(), "yyyy-MM-dd"));
        mEtRiskDiscoverer = findViewById(R.id.et_risk_discoverer);
        mBtSave = findViewById(R.id.bt_save);

        imageAdapter = new ImageAdapter(imgUrls);
        imageAdapter.addChildClickViewIds(R.id.subject_sign_item_delete_iv);
        mRvImg.setLayoutManager(new GridLayoutManager(this, 3));
        mRvImg.setAdapter(imageAdapter);

        addStatusBar(true);

    }

    @Override
    protected void initData() {
        imgUrls.add(new ImageInfo(1, ""));
        imageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initListener() {
        imageAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (imgUrls.get(position).getType() == 1) {
                //startImageSelectActivity();
                PictureFileUtil.openCamera(this);
            } else {
                PictureFileUtil.previewImage(this, position, selectedImgs);
            }
        });
        imageAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.subject_sign_item_delete_iv) {
                deleteImg(imgUrls.get(position).getUrl());
                imageAdapter.notifyDataSetChanged();
            }
        });
        mIvBack.setOnClickListener(new ClickListener());
        mBtSave.setOnClickListener(new ClickListener());
    }

    @Override
    protected void onDelayClick(View view) {
        if (view.getId() == R.id.iv_back) {
            finish();
        } else if (view.getId() == R.id.bt_save) {

            if (StringUtils.isEmpty(mEtRiskRank.getText().toString())) {
                ToastUtils.showShort("请填写隐患级别");
                return;
            }

            if (StringUtils.isEmpty(mEtRiskDescribe.getText().toString())) {
                ToastUtils.showShort("请填写隐患描述");
                return;
            }

            if (imgUrls.size() == 1) {
                ToastUtils.showShort("请至少上传一张图片");
                return;
            }

            if (StringUtils.isEmpty(mEtRiskDiscoverer.getText().toString())) {
                ToastUtils.showShort("请填写发现人");
                return;
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (ImageInfo imageInfo : imgUrls) {
                if (imageInfo.getType() == 0) {
                    stringBuilder.append(imageInfo.getUrl());
                    stringBuilder.append(",");
                }
            }

            CommonDaoUtils<RiskEntity> riskUtils = DaoUtilsStore.getInstance().getRiskUtils();
            RiskEntity riskEntity = new RiskEntity();
            riskEntity.setRiskRank(mEtRiskRank.getText().toString());
            riskEntity.setRiskDescribe(mEtRiskDescribe.getText().toString());
            riskEntity.setDiscoverer(mEtRiskDiscoverer.getText().toString());
            riskEntity.setRiskImg(stringBuilder.toString());
            riskEntity.setRiskTime(mTvRiskTime.getText().toString());
            boolean insert = riskUtils.insert(riskEntity);
            if (insert) {
                ToastUtils.showShort("上传成功");
                EventBusUtils.post(new EventMessage<>(EventCode.CREATE_RISK_SUCCESS));
                finish();
            } else {
                ToastUtils.showShort("上传失败");
            }
        }
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            /**
             * 结果回调
             */
            if (requestCode == PictureConfig.REQUEST_CAMERA) {
                List<LocalMedia> result = PictureSelector.obtainMultipleResult(data);
                selectedImgs.addAll(result);
                if (result.size() != 0) {
                    for (int i = 0; i < result.size(); i++) {
                        imgUrls.add(0, new ImageInfo(result.get(i).getPath(), result.get(i).getRealPath()));
                        String s = Base64Utils.imageToBase64Str(result.get(i).getRealPath());

                        System.out.println(s);
                        LogUtils.eTag("sb", result.get(i).getPath());
                    }

                    if (imgUrls.size() == 4) {
                        imgUrls.remove(3);
                    }
                    imageAdapter.notifyDataSetChanged();


                }
            }
        }
    }

    /**
     * 删除图片
     *
     * @param url 图片地址
     */
    private void deleteImg(String url) {
        Iterator<LocalMedia> iterator = selectedImgs.iterator();
        while (iterator.hasNext()) {
            LocalMedia localMedia = iterator.next();
            if (localMedia.getPath().equals(url)) {
                iterator.remove();
            }
        }

        Iterator<ImageInfo> iterator2 = imgUrls.iterator();
        while (iterator2.hasNext()) {
            ImageInfo imageInfo = iterator2.next();
            if (imageInfo.getUrl().equals(url)) {
                iterator2.remove();
            }
        }

        boolean isHas = false;
        for (ImageInfo imageInfo : imgUrls) {
            if (imageInfo.getType() == 1) {
                isHas = true;
                break;
            }
        }
        if (!isHas) {
            imgUrls.add(new ImageInfo(1, ""));
        }

    }
}