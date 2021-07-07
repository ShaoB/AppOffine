package com.levcn.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.levcn.R;
import com.levcn.greendao.entiy.TaskEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author : shaoBin
 * date   : 2021/7/6 10:26
 * desc   :
 */
public class TaskAdapter extends BaseQuickAdapter<TaskEntity, BaseViewHolder> {

    public TaskAdapter(@Nullable List<TaskEntity> data) {
        super(R.layout.adapter_need_decision, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, TaskEntity taskEntity) {
        baseViewHolder.setText(R.id.tv_name, taskEntity.getF_GZNR())
                .setText(R.id.tv_time, "2021-02-02");
    }
}
