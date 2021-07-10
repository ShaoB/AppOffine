package com.levcn.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.levcn.R;
import com.levcn.activity.RiskDetailActivity;
import com.levcn.activity.TaskDetailActivity;
import com.levcn.adapter.RiskAdapter;
import com.levcn.base.BaseLazyFragment;
import com.levcn.eventbus.EventCode;
import com.levcn.eventbus.EventMessage;
import com.levcn.greendao.entiy.RiskEntity;
import com.levcn.greendao.utils.Constants;
import com.levcn.greendao.utils.DaoUtilsStore;
import com.levcn.greendao.utils.JumpKey;
import com.levcn.greendao.utils.JumpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : shaoBin
 * date   : 2021/7/5 17:51
 * desc   : 发现隐患
 */
public class NoticeFragment extends BaseLazyFragment {

    private List<RiskEntity> mList = new ArrayList<>();
    private RiskAdapter riskAdapter;


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_notice_layout;
    }

    @Override
    protected void loadData() {
        mList.clear();
        mList.addAll(DaoUtilsStore.getInstance().getRiskUtils().queryAll());
        riskAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initViews(View view) {
        RecyclerView mRvNotice = view.findViewById(R.id.rv_notice);
        riskAdapter = new RiskAdapter(mList);
        mRvNotice.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvNotice.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        mRvNotice.setAdapter(riskAdapter);
        riskAdapter.setEmptyView(R.layout.activity_no_risk_null);
    }

    @Override
    protected void initListener() {
        riskAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(JumpKey.RISK_DETAIL, mList.get(position));
                JumpUtils.goNext(mActivity, RiskDetailActivity.class, "bundle", bundle, false);
            }
        });
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Override
    public void onReceiveEvent(EventMessage event) {
        if (event.getCode() == EventCode.CREATE_RISK_SUCCESS) {
            loadData();
        }
    }
}
