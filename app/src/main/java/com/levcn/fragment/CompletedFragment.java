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
import com.levcn.activity.TaskDetailActivity;
import com.levcn.adapter.TaskAdapter;
import com.levcn.base.BaseLazyFragment;
import com.levcn.eventbus.EventCode;
import com.levcn.eventbus.EventMessage;
import com.levcn.greendao.entiy.TaskEntity;
import com.levcn.greendao.entiy.TaskEntityDao;
import com.levcn.greendao.utils.Constants;
import com.levcn.greendao.utils.DaoUtilsStore;
import com.levcn.greendao.utils.JumpKey;
import com.levcn.greendao.utils.JumpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : shaoBin
 * date   : 2021/7/5 17:51
 * desc   : 已办任务
 */
public class CompletedFragment extends BaseLazyFragment {

    private TaskAdapter taskAdapter;

    private List<TaskEntity> mList = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_completed_layout;
    }

    @Override
    protected void loadData() {
        mList.clear();
        mList.addAll(DaoUtilsStore.getInstance().getTaskUtils().queryByQueryBuilder(TaskEntityDao.Properties.State.eq(Constants.TASK_STATE_COMPLETE)));
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initViews(View view) {
        RecyclerView mRvComplete = view.findViewById(R.id.rv_completed);
        taskAdapter = new TaskAdapter(mList);
        mRvComplete.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvComplete.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        mRvComplete.setAdapter(taskAdapter);
        taskAdapter.setEmptyView(R.layout.activity_no_need_decision_null);
    }

    @Override
    protected void initListener() {
        taskAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putInt(JumpKey.TASK_STATE, Constants.TASK_STATE_COMPLETE);
            bundle.putParcelable(JumpKey.TASK_DETAIL, mList.get(position));
            JumpUtils.goNext(mActivity, TaskDetailActivity.class, "bundle", bundle, false);
        });
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Override
    public void onReceiveEvent(EventMessage event) {
        if (event.getCode() == EventCode.NEED_UPDATE_COUNT) {
            loadData();
        }
    }
}
