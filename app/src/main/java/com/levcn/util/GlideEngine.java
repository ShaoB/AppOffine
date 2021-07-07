package com.levcn.util;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.levcn.greendao.utils.GlideHelper;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.listener.OnImageCompleteCallback;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

/**
 * Founder: shaobin
 * Create Date: 2020/4/13
 * Profile:
 */
public class GlideEngine implements ImageEngine {
    private static GlideEngine instance;

    private GlideEngine() {
    }

    public static GlideEngine createGlideEngine() {
        if (null == instance) {
            synchronized (GlideEngine.class) {
                if (null == instance) {
                    instance = new GlideEngine();
                }
            }
        }
        return instance;
    }
    /**
     * 加载图片
     * @param context
     * @param url
     * @param imageView
     */
    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        GlideHelper.loadBitmap(context,url,imageView, GlideHelper.getCommonOptions());
    }

    /**
     * 加载网络图片适配长图方案
     * 注意：此方法只有加载网络图片才会回调
     * @param context
     * @param url
     * @param imageView
     * @param longImageView
     * @param callback 网络图片加载回调监听 {link after version 2.5.1 Please use the #OnImageCompleteCallback#}
     */
    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, SubsamplingScaleImageView longImageView, OnImageCompleteCallback callback) {
        GlideHelper.loadLongUrl(context,url,imageView,longImageView,callback);
    }

    /**
     *  加载网络图片适配长图方案
     * @param context
     * @param url
     * @param imageView
     * @param longImageView
     * @ 已废弃
     */
    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, SubsamplingScaleImageView longImageView) {
    }

    /**
     * 加载相册目录
     * @param context 上下文
     * @param url  图片路径
     * @param imageView 承载图片ImageView
     */
    @Override
    public void loadFolderImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        GlideHelper.loadFolderImage(context,url,imageView);
    }

    /**
     * 加载gif
     * @param context 上下文
     * @param url  图片路径
     * @param imageView 承载图片ImageView
     */
    @Override
    public void loadAsGifImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        GlideHelper.loadAsGifImage(context,url,imageView);
    }

    /**
     * 加载图片列表图片
     * @param context 上下文
     * @param url 图片路径
     * @param imageView 承载图片ImageView
     */
    @Override
    public void loadGridImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        GlideHelper.loadGridImage(context,url,imageView);
    }
}
