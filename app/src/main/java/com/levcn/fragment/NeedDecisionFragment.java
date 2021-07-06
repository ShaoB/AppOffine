package com.levcn.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.levcn.R;
import com.levcn.activity.TaskDetailActivity;
import com.levcn.adapter.NeedDecisionAdapter;
import com.levcn.base.BaseLazyFragment;
import com.levcn.bean.NeedDecisionInfo;
import com.levcn.eventbus.EventBusUtils;
import com.levcn.eventbus.EventCode;
import com.levcn.eventbus.EventMessage;
import com.levcn.greendao.entiy.TaskEntity;
import com.levcn.greendao.utils.DaoUtilsStore;
import com.levcn.greendao.utils.JumpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : shaoBin
 * date   : 2021/7/5 18:22
 * desc   : 待见证
 */
public class NeedDecisionFragment extends BaseLazyFragment {

    private NeedDecisionAdapter needDecisionAdapter;

    private List<TaskEntity> mList = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_need_decision_layout;
    }

    @Override
    protected void loadData() {
        mList.clear();
        mList.addAll(DaoUtilsStore.getInstance().getTaskUtils().queryAll());
        needDecisionAdapter.notifyDataSetChanged();

        EventBusUtils.post(new EventMessage<>(EventCode.NEED_DECISION_COUNT, mList.size()));
    }

    @Override
    protected void initViews(View view) {
        RecyclerView mRvNeedDecision = view.findViewById(R.id.rv_need_decision);

        needDecisionAdapter = new NeedDecisionAdapter(mList);
        mRvNeedDecision.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvNeedDecision.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        mRvNeedDecision.setAdapter(needDecisionAdapter);
        needDecisionAdapter.setEmptyView(R.layout.activity_null);
    }

    @Override
    protected void initListener() {
        needDecisionAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                JumpUtils.goNext(mActivity, TaskDetailActivity.class);
            }
        });
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Override
    public void onReceiveEvent(EventMessage event) {
        if (event.getCode() == EventCode.INSERT_DATA_SUCCESS) {
            loadData();
        }
    }
}
