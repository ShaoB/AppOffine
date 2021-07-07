package com.levcn.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.levcn.greendao.entiy.TaskEntity;
import com.levcn.greendao.entiy.TaskEntityDao;
import com.levcn.greendao.utils.CommonDaoUtils;
import com.levcn.greendao.utils.Constants;
import com.levcn.greendao.utils.DaoUtilsStore;
import com.levcn.greendao.utils.JumpKey;
import com.levcn.presenter.MainPresenter;
import com.levcn.util.PictureFileUtil;
import com.levcn.view.IMainView;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author shaobin
 */
public class TaskRedactActivity extends BaseActivity<IMainView, MainPresenter> implements IMainView {

    private ImageView mIvBack;
    private TextView mTvWorkContent, mTvInspectTime;
    private EditText mEtInspectContent;
    private Button mBtSave;
    private RecyclerView mRvImg;

    private int maxSelectNum = 9;
    /**
     * 已选图片
     */
    private List<LocalMedia> selectedImgs = new ArrayList<>();
    /**
     * 图片
     */
    private List<ImageInfo> imgUrls = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private TaskEntity taskEntity;

    @Override
    protected void onMyCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_task_redact);
    }

    @Override
    protected void initViews() {
        TextView mTvHeaderLayoutContent = findViewById(R.id.header_layout_content_tv);
        mTvHeaderLayoutContent.setText("任务详情");
        mIvBack = findViewById(R.id.iv_back);
        mIvBack.setVisibility(View.VISIBLE);
        mTvWorkContent = findViewById(R.id.tv_work_content);
        mEtInspectContent = findViewById(R.id.et_inspect_content);
        mTvInspectTime = findViewById(R.id.tv_inspect_time);
        mTvInspectTime.setText(TimeUtils.date2String(TimeUtils.getNowDate(), "yyyy-MM-dd"));
        mBtSave = findViewById(R.id.bt_save);
        mRvImg = findViewById(R.id.rv_img);
        imageAdapter = new ImageAdapter(imgUrls);
        imageAdapter.addChildClickViewIds(R.id.subject_sign_item_delete_iv);
        mRvImg.setLayoutManager(new GridLayoutManager(this, 3));
        mRvImg.setAdapter(imageAdapter);

        addStatusBar(true);

        taskEntity = getIntent().getParcelableExtra(JumpKey.TASK_DETAIL);
    }

    @Override
    protected void initData() {
        if (taskEntity != null) {
            mTvWorkContent.setText(taskEntity.getF_GZNR());
        }
        imgUrls.add(new ImageInfo(1, ""));
        imageAdapter.notifyDataSetChanged();
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initListener() {
        mIvBack.setOnClickListener(new ClickListener());
        mBtSave.setOnClickListener(new ClickListener());
        imageAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (imgUrls.get(position).getType() == 1) {
                startImageSelectActivity();
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
    }

    @Override
    protected void onDelayClick(View view) {
        if (view.getId() == R.id.iv_back) {
            finish();
        } else if (view.getId() == R.id.bt_save) {
            if (StringUtils.isEmpty(mEtInspectContent.getText().toString())) {
                ToastUtils.showShort("请填写检查的内容");
                return;
            }

            if (imgUrls.size() == 1) {
                ToastUtils.showShort("请至少上传一张图片");
                return;
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (ImageInfo imageInfo : imgUrls) {
                if (imageInfo.getType() == 0) {
                    stringBuilder.append(imageInfo.getUrl());
                    stringBuilder.append(",");
                }
            }

            CommonDaoUtils<TaskEntity> taskUtils = DaoUtilsStore.getInstance().getTaskUtils();
            taskEntity.setState(Constants.TASK_STATE_NEED_UPDATE);
            taskEntity.setImgUrls(stringBuilder.toString());
            taskEntity.setInspectContent(mEtInspectContent.getText().toString());
            taskEntity.setInspectTime(mTvInspectTime.getText().toString());
            boolean update = taskUtils.update(taskEntity);
            if (update) {
                ToastUtils.showShort("上传成功");
                EventBusUtils.post(new EventMessage<>(EventCode.UPDATE_DATA_SUCCESS));
                finish();
            } else {
                ToastUtils.showShort("上传失败");
            }

        }
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Override
    public void onReceiveEvent(EventMessage event) {

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

    /**
     * 启动相册
     */
    private void startImageSelectActivity() {
        PictureFileUtil.openGallery(this, PictureMimeType.ofImage(), 1, maxSelectNum, selectedImgs, new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(List<LocalMedia> result) {
                selectedImgs.clear();
                imgUrls.clear();
                selectedImgs.addAll(result);
                if (result.size() != 0) {
                    for (int i = 0; i < result.size(); i++) {
                        imgUrls.add(new ImageInfo(result.get(i).getPath()));
                        LogUtils.eTag("sb", result.get(i).getPath());
                    }
                    if (imgUrls.size() < maxSelectNum) {
                        imgUrls.add(new ImageInfo(1, ""));
                    }
                    imageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancel() {
                LogUtils.e("取消");
            }
        });
    }
}