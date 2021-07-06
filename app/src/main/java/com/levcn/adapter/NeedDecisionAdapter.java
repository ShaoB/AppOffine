package com.levcn.adapter;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.levcn.R;
import com.levcn.bean.NeedDecisionInfo;
import com.levcn.greendao.entiy.TaskEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author : shaoBin
 * date   : 2021/7/6 10:26
 * desc   :
 */
public class NeedDecisionAdapter extends BaseQuickAdapter<TaskEntity, BaseViewHolder> {

    public NeedDecisionAdapter(@Nullable List<TaskEntity> data) {
        super(R.layout.adapter_need_decision, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, TaskEntity taskEntity) {
        baseViewHolder.setText(R.id.tv_name, taskEntity.getTask_name())
                .setText(R.id.tv_time, taskEntity.getTask_time());
    }
}
