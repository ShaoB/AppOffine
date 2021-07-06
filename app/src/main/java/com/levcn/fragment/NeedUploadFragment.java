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
 * date   : 2021/7/5 18:19
 * desc   : 待上传
 */
public class NeedUploadFragment extends BaseLazyFragment {

    private RecyclerView mRvNeedUpdate;
    private NeedDecisionAdapter needUpdateAdapter;

    private List<NeedDecisionInfo> mList = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_need_upload_layout;
    }

    @Override
    protected void loadData() {
        for (int i = 0; i < 16; i++) {
            mList.add(new NeedDecisionInfo("待上传任务" + (i + 1), 1622536382 - (i * 100)));
        }
        needUpdateAdapter.notifyDataSetChanged();

        EventBusUtils.post(new EventMessage<>(EventCode.NEED_UPDATE_COUNT, mList.size()));
    }

    @Override
    protected void initViews(View view) {
        mRvNeedUpdate = view.findViewById(R.id.rv_need_update);

        needUpdateAdapter = new NeedDecisionAdapter(mList);
        mRvNeedUpdate.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvNeedUpdate.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        mRvNeedUpdate.setAdapter(needUpdateAdapter);
        needUpdateAdapter.setEmptyView(R.layout.activity_null);
    }
}
