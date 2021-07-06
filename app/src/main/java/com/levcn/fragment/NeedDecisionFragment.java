package com.levcn.fragment;

import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.levcn.R;
import com.levcn.adapter.NeedDecisionAdapter;
import com.levcn.base.BaseLazyFragment;
import com.levcn.bean.NeedDecisionInfo;
import com.levcn.eventbus.EventBusUtils;
import com.levcn.eventbus.EventCode;
import com.levcn.eventbus.EventMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : shaoBin
 * date   : 2021/7/5 18:22
 * desc   : 待见证
 */
public class NeedDecisionFragment extends BaseLazyFragment {

    private RecyclerView mRvNeedDecision;
    private NeedDecisionAdapter needDecisionAdapter;

    private List<NeedDecisionInfo> mList = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_need_decision_layout;
    }

    @Override
    protected void loadData() {
        for (int i = 0; i < 10; i++) {
            mList.add(new NeedDecisionInfo("待办任务" + (i + 1), 1622536382 - (i * 100)));
        }
        needDecisionAdapter.notifyDataSetChanged();

        EventBusUtils.post(new EventMessage<>(EventCode.NEED_DECISION_COUNT, mList.size()));
    }

    @Override
    protected void initViews(View view) {
        mRvNeedDecision = view.findViewById(R.id.rv_need_decision);

        needDecisionAdapter = new NeedDecisionAdapter(mList);
        mRvNeedDecision.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvNeedDecision.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        mRvNeedDecision.setAdapter(needDecisionAdapter);
        needDecisionAdapter.setEmptyView(R.layout.activity_null);
    }
}
