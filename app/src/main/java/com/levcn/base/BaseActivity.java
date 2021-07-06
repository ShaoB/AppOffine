package com.levcn.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.levcn.eventbus.EventBusUtils;
import com.levcn.eventbus.EventMessage;
import com.levcn.listener.NoDoubleClickListener;
import com.levcn.manager.ActivityCollector;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author : shaoBin
 * date   : 2021/7/5 16:57
 * desc   : 基类
 */
public abstract class BaseActivity<V extends IBaseView, T extends BasePresenter<V>> extends AppCompatActivity {
    protected T mPresenter;
    protected Context mContext;
    protected Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);

        //写死竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.mPresenter = this.createPresenter();
        this.mPresenter.attach((V) this);

        this.mActivity = this;
        this.mContext = this.mActivity.getApplicationContext();
        this.onMyCreate(savedInstanceState);
        this.initViews();
        this.initData();
        this.initListener();

        if (isRegisteredEventBus()) {
            EventBusUtils.register(this);
        }
    }

    @Override
    public Resources getResources() {
        //字体大小不跟随系统改变
        Resources res = super.getResources();
        Configuration newConfig = new Configuration();
        newConfig.setToDefaults();//设置默认
        res.updateConfiguration(newConfig, res.getDisplayMetrics());
        return res;
    }

    public class ClickListener extends NoDoubleClickListener {
        @Override
        public void onNoDoubleClick(View v) {
            onDelayClick(v);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(mActivity);
        if (isRegisteredEventBus()) {
            EventBusUtils.unregister(this);
        }
    }

    protected abstract void onMyCreate(Bundle savedInstanceState);

    protected abstract void initViews();

    public abstract T createPresenter();

    protected void initData() {

    }

    protected void initListener() {

    }

    protected void onDelayClick(View view) {
    }

    /**
     * 是否注册事件分发
     *
     * @return true 注册；false 不注册，默认不注册
     */
    protected boolean isRegisteredEventBus() {
        return false;
    }

    /**
     * 接收到分发的事件
     *
     * @param event 事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(EventMessage event) {
    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveStickyEvent(EventMessage event) {
    }
}
