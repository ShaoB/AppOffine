package com.levcn.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.levcn.R;
import com.levcn.eventbus.EventBusUtils;
import com.levcn.eventbus.EventMessage;
import com.levcn.listener.NoDoubleClickListener;
import com.levcn.widget.UiHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * @author shaobin
 */
public abstract class BaseLazyFragment extends Fragment {

    /**
     * 标志位 判断数据是否初始化
     */
    private boolean isInitData = false;
    /**
     * 标志位 判断fragment是否可见
     */
    private boolean isVisibleToUser = true;
    /**
     * 标志位 判断view已经加载完成 避免空指针操作
     */
    private boolean isPrepareView = false;

    protected FragmentActivity mActivity;


    /**
     * 设置布局
     */
    protected abstract int setLayoutId();

    /**
     * 加载数据
     */
    protected abstract void loadData();

    /**
     * 初始化布局
     */
    protected abstract void initViews(View view);

    protected void onDelayClick(View view) {
    }

    protected void initListener() {
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        this.mActivity = (FragmentActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isRegisteredEventBus()) {
            EventBusUtils.register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(setLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepareView = true;
        initViews(view);
        initListener();
    }

    /**
     * 当fragment由可见变为不可见和不可见变为可见时回调
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        lazyInitData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazyInitData();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isRegisteredEventBus()) {
            EventBusUtils.unregister(this);
        }
    }

    public class ClickListener extends NoDoubleClickListener {
        @Override
        public void onNoDoubleClick(View v) {
            onDelayClick(v);
        }
    }

    /**
     * 懒加载方法
     */
    private void lazyInitData() {
        if (!isInitData && isVisibleToUser && isPrepareView) {
            isInitData = true;
            loadData();
        }
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

    public void addStatusBar(View view) {
        ViewGroup headerView = view.findViewById(R.id.header_content_layout);
        if (headerView.getChildCount() == 0) {
            View statusView = UiHelper.createStatusView(this.mActivity, Color.TRANSPARENT);
            headerView.addView(statusView, 0);
        }
    }
}
