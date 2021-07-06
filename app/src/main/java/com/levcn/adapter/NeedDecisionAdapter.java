package com.levcn.adapter;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.levcn.R;
import com.levcn.bean.NeedDecisionInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author : shaoBin
 * date   : 2021/7/6 10:26
 * desc   :
 */
public class NeedDecisionAdapter extends BaseQuickAdapter<NeedDecisionInfo, BaseViewHolder> {

    public NeedDecisionAdapter(@Nullable List<NeedDecisionInfo> data) {
        super(R.layout.adapter_need_decision, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, NeedDecisionInfo needDecisionInfo) {
        baseViewHolder.setText(R.id.tv_name, needDecisionInfo.getTaskName())
                .setText(R.id.tv_time, TimeUtils.millis2String(needDecisionInfo.getTaskTime(), "yyyy-MM-dd"));
    }
}
