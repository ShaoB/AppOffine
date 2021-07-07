package com.levcn.greendao.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.levcn.R;
import com.luck.picture.lib.listener.OnImageCompleteCallback;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

/**
 * Founder: shaobin
 * Create Date: 2020/3/17
 * Profile:Glide 图片加载
 */
public class GlideHelper {

    /**
     * 通用设置
     * <p>
     * 磁盘缓存参数 diskCacheStrategy
     * DiskCacheStrategy.NONE： 表示不缓存任何内容。
     * DiskCacheStrategy.DATA： 表示只缓存原始图片。
     * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
     * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
     * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）
     */
    public static RequestOptions getCommonOptions() {
        return new RequestOptions()
                .placeholder(R.mipmap.default_pic)//占位符
                .error(R.mipmap.default_photo_error)//失败
                .fallback(R.mipmap.default_pic)//为 null 时展示
                .format(DecodeFormat.PREFER_RGB_565);
    }


    /**
     * 加载图片Url
     */
    public static void load(Context mContext, String url, ImageView imageView, RequestOptions options, RequestListener requestListener) {
        if (mContext != null) {
            if (requestListener != null) {
                Glide.with(mContext.getApplicationContext())
                        .load(url)
                        .apply(options)
                        .listener(requestListener)
                        .into(imageView);
            } else {
                Glide.with(mContext.getApplicationContext())
                        .load(url)
                        .apply(options)
                        .into(imageView);
            }
        }
    }

    /**
     * 加载静态图片Url
     */
    public static void loadBitmap(Context mContext, String url, ImageView imageView, RequestOptions options) {
        if (mContext != null) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(url)
                    .apply(options)
                    .into(imageView);
        }
    }

    /**
     * 加载文件图片Url
     */
    public static void loadFile(Context mContext, String url, ImageView imageView, RequestOptions options) {
        if (mContext != null) {
            Glide.with(mContext.getApplicationContext())
                    .asFile()
                    .load(url)
                    .apply(options)
                    .into(imageView);
        }
    }

    /**
     * 加载文件图片Url
     */
    public static void loadUri(Context mContext, Uri uri, ImageView imageView, RequestOptions options) {
        if (mContext != null) {
            Glide.with(mContext.getApplicationContext())
                    .asBitmap()
                    .load(uri)
                    .apply(options)
                    .into(imageView);
        }
    }

    /**
     * 加载相册目录
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadFolderImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .override(180, 180)
                .centerCrop()
                .sizeMultiplier(0.5f)
                .apply(new RequestOptions().placeholder(R.drawable.picture_image_placeholder))
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.
                                        create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(8);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    /**
     * 加载gif图片
     */
    public static void loadAsGifImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .asGif()
                .load(url)
                .into(imageView);
    }

    /**
     * 加载图片列表图片
     */
    public static void loadGridImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .override(200, 200)
                .centerCrop()
                .apply(new RequestOptions().placeholder(R.drawable.picture_image_placeholder))
                .into(imageView);
    }

    /**
     * 加载高清大图
     */
    public static void loadLongUrl(Context mContext, String url, ImageView imageView, SubsamplingScaleImageView longImageView, OnImageCompleteCallback callback) {
        if (mContext != null) {
            loadBitmap(mContext, url, imageView, getCommonOptions());
        }

        /*if (mContext != null) {
            Glide.with(mContext.getApplicationContext())
                    .load(url)
                    .placeholder(R.mipmap.szcs_bannner)
                    .error(R.mipmap.szcs_bannner)
                    .format(DecodeFormat.PREFER_RGB_565)
                    // 取消动画，防止第一次加载不出来
                    .dontAnimate()
                    //加载缩略图
                    .thumbnail(0.3f)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new ImageViewTarget<Drawable>(imageView) {
                        @Override
                        public void onLoadStarted(@Nullable Drawable placeholder) {
                            super.onLoadStarted(placeholder);
                            if (callback != null) {
                                callback.onShowLoading();
                            }
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            if (callback != null) {
                                callback.onHideLoading();
                            }
                        }

                        @Override
                        protected void setResource(@Nullable Drawable resource) {
                            if (callback != null) {
                                callback.onHideLoading();
                            }
                            if (resource != null) {
                                boolean eqLongImage = MediaUtils.isLongImg(resource.getWidth(),
                                        resource.getHeight());
                                longImageView.setVisibility(eqLongImage ? View.VISIBLE : View.GONE);
                                imageView.setVisibility(eqLongImage ? View.GONE : View.VISIBLE);
                                if (eqLongImage) {
                                    // 加载长图
                                    longImageView.setQuickScaleEnabled(true);
                                    longImageView.setZoomEnabled(true);
                                    longImageView.setPanEnabled(true);
                                    longImageView.setDoubleTapZoomDuration(100);
                                    longImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
                                    longImageView.setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER);
                                    longImageView.setImage(ImageSource.bitmap(resource),
                                            new ImageViewState(0, new PointF(0, 0), 0));
                                } else {
                                    // 普通图片
                                    imageView.setImageDrawable(resource);
                                }
                            }
                        }
                    });
        }*/
    }
}
