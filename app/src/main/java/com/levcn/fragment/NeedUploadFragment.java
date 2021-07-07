package com.levcn.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.levcn.R;
import com.levcn.activity.TaskDetailActivity;
import com.levcn.adapter.TaskAdapter;
import com.levcn.base.BaseLazyFragment;
import com.levcn.eventbus.EventBusUtils;
import com.levcn.eventbus.EventCode;
import com.levcn.eventbus.EventMessage;
import com.levcn.greendao.entiy.TaskEntity;
import com.levcn.greendao.entiy.TaskEntityDao;
import com.levcn.greendao.utils.Constants;
import com.levcn.greendao.utils.DaoUtilsStore;
import com.levcn.greendao.utils.JumpKey;
import com.levcn.greendao.utils.JumpUtils;
import com.levcn.listener.ClientHandler;
import com.levcn.service.Server;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : shaoBin
 * date   : 2021/7/5 18:19
 * desc   : 待上传
 */
public class NeedUploadFragment extends BaseLazyFragment {

    private RecyclerView mRvNeedUpdate;
    private TaskAdapter needUpdateAdapter;

    private List<TaskEntity> mList = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_need_upload_layout;
    }

    @Override
    protected void loadData() {
        mList.clear();
        mList.addAll(DaoUtilsStore.getInstance().getTaskUtils().queryByQueryBuilder(TaskEntityDao.Properties.State.eq(Constants.TASK_STATE_NEED_UPDATE)));
        needUpdateAdapter.notifyDataSetChanged();

        EventBusUtils.post(new EventMessage<>(EventCode.NEED_UPDATE_COUNT, mList.size()));
    }

    @Override
    protected void initViews(View view) {
        mRvNeedUpdate = view.findViewById(R.id.rv_need_update);

        needUpdateAdapter = new TaskAdapter(mList);
        mRvNeedUpdate.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvNeedUpdate.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        mRvNeedUpdate.setAdapter(needUpdateAdapter);
        needUpdateAdapter.setEmptyView(R.layout.activity_no_need_decision_null);
    }

    @Override
    protected void initListener() {
        needUpdateAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putInt(JumpKey.TASK_STATE, Constants.TASK_STATE_NEED_UPDATE);
            bundle.putParcelable(JumpKey.TASK_DETAIL, mList.get(position));
            JumpUtils.goNext(mActivity, TaskDetailActivity.class, "bundle", bundle, false);
        });
    }

    @Override
    protected void onDelayClick(View view) {

    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Override
    public void onReceiveEvent(EventMessage event) {
        if (event.getCode() == EventCode.UPDATE_DATA_SUCCESS) {
            loadData();
        }
    }
}
