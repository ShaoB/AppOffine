package com.levcn.activity;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.levcn.R;
import com.levcn.base.BaseActivity;
import com.levcn.greendao.entiy.TaskEntity;
import com.levcn.greendao.utils.JumpKey;
import com.levcn.greendao.utils.JumpUtils;
import com.levcn.presenter.MainPresenter;
import com.levcn.view.IMainView;

import java.util.List;

/**
 * @author shaobin
 */
public class CaptureMicaActivity extends BaseActivity<IMainView, MainPresenter> implements IMainView, BarcodeCallback {

    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private ImageView mIvBack;
    private TaskEntity taskEntity;

    @Override
    protected void onMyCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_capture_mica);
        this.barcodeScannerView = findViewById(R.id.activity_capture_mica_barcode_view);
        this.capture = new CaptureManager(this, this.barcodeScannerView);
        this.capture.initializeFromIntent(getIntent(), savedInstanceState);
        this.capture.decode();
    }

    @Override
    protected void initViews() {
        TextView mTvHeaderLayoutContent = findViewById(R.id.header_layout_content_tv);
        mTvHeaderLayoutContent.setText("扫描二维码");
        mIvBack = findViewById(R.id.iv_back);
        mIvBack.setVisibility(View.VISIBLE);

        addStatusBar(true);

        taskEntity = getIntent().getParcelableExtra(JumpKey.TASK_DETAIL);
    }


    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.capture.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.capture.onSaveInstanceState(outState);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        this.capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return this.barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public void initListener() {
        this.barcodeScannerView.decodeSingle(this);
        mIvBack.setOnClickListener(new ClickListener());
    }

    @Override
    protected void onDelayClick(View view) {
        if (view.getId() == R.id.iv_back) {
            finish();
        }
    }

    @Override
    public void barcodeResult(BarcodeResult result) {
        if (StringUtils.isEmpty(result.getText())) {
            ToastUtils.showShort("扫码失败,请稍后重试");
            super.finish();
        } else {
            ToastUtils.showShort("扫码成功：" + result.getText());
            LogUtils.eTag("sb" + "二维码结果：" + result.getText());
            Bundle bundle = new Bundle();
            bundle.putParcelable(JumpKey.TASK_DETAIL, taskEntity);
            JumpUtils.goNext(CaptureMicaActivity.this, TaskRedactActivity.class, "bundle", bundle, true);
        }
    }

    @Override
    public void possibleResultPoints(List<ResultPoint> resultPoints) {

    }
}