package com.levcn.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.levcn.R;
import com.levcn.greendao.entiy.RiskEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author : shaoBin
 * date   : 2021/7/6 10:26
 * desc   :
 */
public class RiskAdapter extends BaseQuickAdapter<RiskEntity, BaseViewHolder> {

    public RiskAdapter(@Nullable List<RiskEntity> data) {
        super(R.layout.adapter_need_decision, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RiskEntity riskEntity) {
        baseViewHolder.setText(R.id.tv_name, riskEntity.getRiskDescribe())
                .setText(R.id.tv_time, riskEntity.getRiskTime());
    }
}
