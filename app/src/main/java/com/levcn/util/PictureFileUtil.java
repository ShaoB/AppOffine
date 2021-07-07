package com.levcn.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;

import com.levcn.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.List;

/**
 * Founder: shaobin
 * Create Date: 2020/4/13
 * Profile:
 */
public class PictureFileUtil {


    /**
     * 单独打开相机
     *
     * @param mContext
     */
    public static void openCamera(Activity mContext) {
        PictureSelector.create(mContext)
                .openCamera(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                .forResult(PictureConfig.REQUEST_CAMERA);
    }

    /**
     * 打开相册
     *
     * @param mContext
     * @param type
     * @param minSelectNum
     * @param maxSelectNum
     */
    public static void openGallery(Activity mContext, int type, int minSelectNum, int maxSelectNum, List<LocalMedia> list, OnResultCallbackListener<LocalMedia> onResultCallbackListener) {
        String pictureMimeType;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            pictureMimeType = PictureMimeType.PNG_Q;
        } else {
            pictureMimeType = PictureMimeType.JPEG;
        }
        PictureSelector.create(mContext)
                .openGallery(type)//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                //.theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                //.setPictureStyle()// 动态自定义相册主题  注意：此方法最好不要与.theme();同时存在， 二选一
                //.setPictureCropStyle()// 动态自定义裁剪主题
                //.setPictureWindowAnimationStyle()// 自定义相册启动退出动画
                .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项   参考Demo MainActivity中代码
                //.loadCacheResourcesCallback()// 获取图片加载框架资源缓存
                //.isWithVideoImage(false)// 图片和视频是否可以同选,只在ofAll模式下有效
                .isUseCustomCamera(false)// 是否使用自定义相机，5.0以下请不要使用，可能会出现兼容性问题
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)// 设置相册Activity方向，不设置默认使用系统
                .isOriginalImageControl(false)// 是否显示原图控制按钮，如果用户勾选了 压缩、裁剪功能将会失效
                .isWeChatStyle(true)// 是否开启微信图片选择风格，此开关开启了才可使用微信主题！！！
                .isAndroidQTransform(false)// 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对compress(false); &&enableCrop(false)有效
                //.bindCustomPlayVideoCallback(callback)// 自定义播放回调控制，用户可以使用自己的视频播放界面
                //.bindPictureSelectorInterfaceListener(interfaceListener)// 提供给用户的一些额外的自定义操作回调
                .isMultipleSkipCrop(false)// 多图裁剪时是否支持跳过，默认支持
                .isMultipleRecyclerAnimation(false)// 多图裁剪底部列表显示动画效果
                //.setLanguage()// 设置语言，默认中文
                .maxSelectNum(maxSelectNum)// 最大图片选择数量 int
                .minSelectNum(minSelectNum)// 最小选择数量 int
                .minVideoSelectNum(minSelectNum)// 视频最小选择数量，如果没有单独设置的需求则可以不设置，同用minSelectNum字段
                .maxVideoSelectNum(maxSelectNum) // 视频最大选择数量，如果没有单独设置的需求则可以不设置，同用maxSelectNum字段
                .imageSpanCount(3)// 每行显示个数 int
                .isReturnEmpty(true)// 未选择数据时点击按钮是否可以返回
                .isNotPreviewDownload(false)// 预览图片长按是否可以下载
                .queryMaxFileSize(10)// 只查多少M以内的图片、视频、音频  单位M
                //.querySpecifiedFormatSuffix(PictureMimeType.ofPNG())// 查询指定后缀格式资源
                //.setOutputCameraPath()// 自定义相机输出目录，只针对Android Q以下，例如 Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) +  File.separator + "Camera" + File.separator;
                //.cameraFileName("test.png") // 重命名拍照文件名、注意这个只在使用相机时可以使用
                //.renameCompressFile("test.png")// 重命名压缩文件名、 注意这个不要重复，只适用于单张图压缩使用
                //.renameCropFileName("test.png")// 重命名裁剪文件名、 注意这个不要重复，只适用于单张图裁剪使用
                .isSingleDirectReturn(true)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                //.setTitleBarBackgroundColor()//相册标题栏背景色
                //.isChangeStatusBarFontColor()// 是否关闭白色状态栏字体颜色
                //.setStatusBarColorPrimaryDark()// 状态栏背景色
                //.setUpArrowDrawable()// 设置标题栏右侧箭头图标
                // .setDownArrowDrawable()// 设置标题栏右侧箭头图标
                //.isOpenStyleCheckNumMode()// 是否开启数字选择模式 类似QQ相册
                .selectionMode(maxSelectNum > minSelectNum ?
                        PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(true)// 是否可预览视频 true or false
                .enablePreviewAudio(true) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(pictureMimeType)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .enableCrop(false)// 是否裁剪 true or false
                //.setCircleDimmedColor()// 设置圆形裁剪背景色值
                //.setCircleDimmedBorderColor()// 设置圆形裁剪边框色值
                //.setCircleStrokeWidth(3)// 设置圆形裁剪边框粗细
                .compress(true)// 是否压缩 true or false
                .glideOverride(160, 160)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                //.withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
                //.compressSavePath(getPath())//压缩图片保存地址
                //.freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                //.circleDimmedLayer(false)// 是否圆形裁剪 true or false
                //.showCropFrame()// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                //.showCropGrid()// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                //.openClickSound()// 是否开启点击声音 true or false
                .selectionMedia(list)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 废弃 改用cutOutQuality()
                .cutOutQuality(90)// 裁剪输出质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.cropImageWideHigh()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled() // 裁剪是否可旋转图片 true or false
                //.scaleEnabled()// 裁剪是否可放大缩小图片 true or false
                //.videoQuality()// 视频录制质量 0 or 1 int
                .videoMaxSecond(30)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                //.recordVideoSecond()//视频秒数录制 默认60s int
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                //.forResult(requstCode);//结果回调onActivityResult code
                .forResult(onResultCallbackListener);//Callback回调方式
    }

    /**
     * 打开相册 剪切功能
     *
     * @param mContext
     * @param minSelectNum
     * @param maxSelectNum
     */
    public static void openGalleryCrop(Activity mContext, int minSelectNum, int maxSelectNum, OnResultCallbackListener<LocalMedia> onResultCallbackListener) {
        String pictureMimeType;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            pictureMimeType = PictureMimeType.PNG_Q;
        } else {
            pictureMimeType = PictureMimeType.JPEG;
        }
        PictureSelector.create(mContext)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                //.theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                //.setPictureStyle()// 动态自定义相册主题  注意：此方法最好不要与.theme();同时存在， 二选一
                //.setPictureCropStyle()// 动态自定义裁剪主题
                //.setPictureWindowAnimationStyle()// 自定义相册启动退出动画
                .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项   参考Demo MainActivity中代码
                //.loadCacheResourcesCallback()// 获取图片加载框架资源缓存
                //.isWithVideoImage(false)// 图片和视频是否可以同选,只在ofAll模式下有效
                .isUseCustomCamera(false)// 是否使用自定义相机，5.0以下请不要使用，可能会出现兼容性问题
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)// 设置相册Activity方向，不设置默认使用系统
                .isOriginalImageControl(false)// 是否显示原图控制按钮，如果用户勾选了 压缩、裁剪功能将会失效
                .isWeChatStyle(true)// 是否开启微信图片选择风格，此开关开启了才可使用微信主题！！！
                .isAndroidQTransform(false)// 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对compress(false); &&enableCrop(false)有效
                //.bindCustomPlayVideoCallback(callback)// 自定义播放回调控制，用户可以使用自己的视频播放界面
                //.bindPictureSelectorInterfaceListener(interfaceListener)// 提供给用户的一些额外的自定义操作回调
                .isMultipleSkipCrop(false)// 多图裁剪时是否支持跳过，默认支持
                .isMultipleRecyclerAnimation(false)// 多图裁剪底部列表显示动画效果
                //.setLanguage()// 设置语言，默认中文
                .maxSelectNum(maxSelectNum)// 最大图片选择数量 int
                .minSelectNum(minSelectNum)// 最小选择数量 int
                .minVideoSelectNum(minSelectNum)// 视频最小选择数量，如果没有单独设置的需求则可以不设置，同用minSelectNum字段
                .maxVideoSelectNum(maxSelectNum) // 视频最大选择数量，如果没有单独设置的需求则可以不设置，同用maxSelectNum字段
                .imageSpanCount(3)// 每行显示个数 int
                .isReturnEmpty(true)// 未选择数据时点击按钮是否可以返回
                .isNotPreviewDownload(false)// 预览图片长按是否可以下载
                .queryMaxFileSize(10)// 只查多少M以内的图片、视频、音频  单位M
                //.querySpecifiedFormatSuffix(PictureMimeType.ofPNG())// 查询指定后缀格式资源
                //.setOutputCameraPath()// 自定义相机输出目录，只针对Android Q以下，例如 Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) +  File.separator + "Camera" + File.separator;
                //.cameraFileName("test.png") // 重命名拍照文件名、注意这个只在使用相机时可以使用
                //.renameCompressFile("test.png")// 重命名压缩文件名、 注意这个不要重复，只适用于单张图压缩使用
                //.renameCropFileName("test.png")// 重命名裁剪文件名、 注意这个不要重复，只适用于单张图裁剪使用
                .isSingleDirectReturn(true)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                //.setTitleBarBackgroundColor()//相册标题栏背景色
                //.isChangeStatusBarFontColor()// 是否关闭白色状态栏字体颜色
                //.setStatusBarColorPrimaryDark()// 状态栏背景色
                //.setUpArrowDrawable()// 设置标题栏右侧箭头图标
                // .setDownArrowDrawable()// 设置标题栏右侧箭头图标
                //.isOpenStyleCheckNumMode()// 是否开启数字选择模式 类似QQ相册
                .selectionMode(maxSelectNum > minSelectNum ?
                        PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(true)// 是否可预览视频 true or false
                .enablePreviewAudio(true) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(pictureMimeType)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .enableCrop(true)// 是否裁剪 true or false
                .setCircleDimmedColor(R.color.color_c51724)// 设置圆形裁剪背景色值
                .setCircleDimmedBorderColor(R.color.color_2c9bf4)// 设置圆形裁剪边框色值
                .setCircleStrokeWidth(3)// 设置圆形裁剪边框粗细
                .compress(true)// 是否压缩 true or false
                .glideOverride(160, 160)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
                //.compressSavePath(getPath())//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                //.openClickSound()// 是否开启点击声音 true or false
                //.selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cutOutQuality(90)// 裁剪输出质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.cropImageWideHigh()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                //.videoQuality()// 视频录制质量 0 or 1 int
                .videoMaxSecond(30)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                //.recordVideoSecond()//视频秒数录制 默认60s int
                .isDragFrame(true)// 是否可拖动裁剪框(固定)
                //.forResult(requstCode);//结果回调onActivityResult code
                .forResult(onResultCallbackListener);//Callback回调方式
    }

    /**
     * 预览图片
     *
     * @param mContext
     * @param position
     * @param selectList
     */
    public static void previewImage(Activity mContext, int position, List<LocalMedia> selectList) {
        // 预览图片 可自定长按保存路径
        //注意 .themeStyle(R.style.theme)；里面的参数不可删，否则闪退...
        PictureSelector.create(mContext)
                .themeStyle(R.style.picture_default_style)
                .isNotPreviewDownload(true)
                .loadImageEngine(GlideEngine.createGlideEngine())
                .openExternalPreview(position, selectList);
    }

    public static void previewVideo(Activity mContext, String videoPath) {
        PictureSelector.create(mContext).externalPictureVideo(videoPath);
    }

    public static void openFile(Activity mContext, int requestCode) {
        /*new MaterialFilePicker()
                .withActivity(mContext)
                .withRequestCode(requestCode)
                //       .withFilter(Pattern.compile(".*\\.txt$")) // Filtering files and directories by file name using regexp
                .withFilterDirectories(true) // Set directories filterable (false by default)
                .withHiddenFiles(true) // Show hidden files and folders
                .start();*/
    }

}
