package com.levcn.activity;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
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
import com.levcn.presenter.MainPresenter;
import com.levcn.view.IMainView;

import java.util.List;

public class CaptureMicaActivity extends BaseActivity<IMainView, MainPresenter> implements IMainView, BarcodeCallback {

    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;

    @Override
    protected void onMyCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_capture_mica);
        this.barcodeScannerView = (DecoratedBarcodeView) super.findViewById(R.id.activity_capture_mica_barcode_view);
        this.capture = new CaptureManager(this, this.barcodeScannerView);
        this.capture.initializeFromIntent(getIntent(), savedInstanceState);
        this.capture.decode();
    }

    @Override
    protected void initViews() {
        TextView mTvHeaderLayoutContent = findViewById(R.id.header_layout_content_tv);
        mTvHeaderLayoutContent.setText("扫描二维码");

        addStatusBar(false);
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
    }

    @Override
    public void barcodeResult(BarcodeResult result) {
        if (StringUtils.isEmpty(result.getText())) {
            ToastUtils.showShort("扫码失败,请稍后重试");
            super.finish();
        } else {
            LogUtils.e(result.getText());
            ToastUtils.showShort("扫码成功：" + result.getText());
        }
    }

    @Override
    public void possibleResultPoints(List<ResultPoint> resultPoints) {

    }
}