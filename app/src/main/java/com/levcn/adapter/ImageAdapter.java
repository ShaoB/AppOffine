package com.levcn.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.levcn.R;
import com.levcn.bean.ImageInfo;
import com.levcn.greendao.utils.GlideHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author : shaoBin
 * date   : 2021/7/7 13:55
 * desc   :
 */
public class ImageAdapter extends BaseQuickAdapter<ImageInfo, BaseViewHolder> {


    public ImageAdapter(@Nullable List<ImageInfo> data) {
        super(R.layout.adapter_image_layout, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ImageInfo imageInfo) {
        ImageView mIv = baseViewHolder.getView(R.id.subject_sign_item_img_iv);
        if (imageInfo.getType() == 0) {
            GlideHelper.loadBitmap(getContext(), imageInfo.getUrl(), mIv, GlideHelper.getCommonOptions());
            if (imageInfo.getShowType() == 0) {
                baseViewHolder.setGone(R.id.subject_sign_item_delete_iv, false);
            } else {
                baseViewHolder.setGone(R.id.subject_sign_item_delete_iv, true);
            }
        } else {
            mIv.setImageResource(R.mipmap.icon1_pic);
            baseViewHolder.setGone(R.id.subject_sign_item_delete_iv, true);
        }
    }
}
