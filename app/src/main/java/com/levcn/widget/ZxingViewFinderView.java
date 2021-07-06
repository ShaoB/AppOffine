package com.levcn.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.Size;
import com.journeyapps.barcodescanner.ViewfinderView;
import com.levcn.R;

import java.util.ArrayList;
import java.util.List;

public class ZxingViewFinderView extends ViewfinderView {

    /**
     * viewfinder color
     */
    private int scannerBoundColor;
    /**
     * viewfinder width
     */
    private float scannerBoundWidth;
    /**
     * viewfinder corner color
     */
    private int scannerBoundCornerColor;
    /**
     * viewfinder corner width
     */
    private float scannerBoundCornerWidth;
    /**
     * viewfinder corner height
     */
    private float scannerBoundCornerHeight;
    /**
     * 扫码线资源
     */
    private int scannerLaserResId;
    /**
     * laser bitmap
     */
    private Bitmap scannerLaserBitmap;
    /**
     * laser top position
     */
    private int scannerLaserTop;

    private int LASER_MOVE_DIRECTION = 1;
    private final static int LASER_MOVE_DISTANCE_PER_UNIT_TIME = 10;

    public ZxingViewFinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources resources = getResources();
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.ZxingViewFinderView);
        scannerBoundColor = attributes.getColor(R.styleable.ZxingViewFinderView_scannerBoundColor, resources.getColor(R.color.white));
        scannerBoundWidth = attributes.getDimension(R.styleable.ZxingViewFinderView_scannerBoundWidth, 0.5f);
        scannerBoundCornerColor = attributes.getColor(R.styleable.ZxingViewFinderView_scannerBoundCornerColor, resources.getColor(R.color.yellow));
        scannerBoundCornerWidth = attributes.getDimension(R.styleable.ZxingViewFinderView_scannerBoundCornerWith, 1.5f);
        scannerBoundCornerHeight = attributes.getDimension(R.styleable.ZxingViewFinderView_scannerBoundCornerHeight, 24f);
        scannerLaserResId = attributes.getResourceId(R.styleable.ZxingViewFinderView_scannerLaserResId, 0);
    }

    /**
     * 绘制外观(即框架外矩形)变暗
     *
     * @param canvas
     * @param frame
     * @param width
     * @param height
     */
    private void drawExteriorDarkened(Canvas canvas, Rect frame, int width, int height) {
        this.paint.setColor(this.resultBitmap != null ? this.resultColor : this.maskColor);
        canvas.drawRect(0, 0, width, frame.top, this.paint);    //top
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, this.paint);    //left
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, this.paint);   //right
        canvas.drawRect(0, frame.bottom + 1, width, height, this.paint);    //bottom
    }

    @Override
    public void onDraw(Canvas canvas) {
        refreshSizes();
        if (this.framingRect == null || this.previewSize == null) {
            return;
        }
        Rect frame = this.framingRect;

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        this.drawExteriorDarkened(canvas, frame, width, height);

        if (this.resultBitmap != null) {
            this.paint.setAlpha(CURRENT_POINT_OPACITY);
            canvas.drawBitmap(this.resultBitmap, null, frame, this.paint);
        } else {
            drawFrameBound(canvas, frame);
            drawFrameCorner(canvas, frame);
            drawLaserLine(canvas, frame);
            drawResultPoint(canvas, frame, previewSize);
            postInvalidateDelayed(ANIMATION_DELAY,
                    frame.left - POINT_SIZE,
                    frame.top - POINT_SIZE,
                    frame.right + POINT_SIZE,
                    frame.bottom + POINT_SIZE);
        }
    }

    /**
     * 画出扫码进度条
     *
     * @param canvas
     * @param frame
     */
    private void drawLaserLine(Canvas canvas, Rect frame) {
        if (scannerLaserResId == 0) {
            paint.setColor(laserColor);
            paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
            scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
            int middle = frame.height() / 2 + frame.top;
            canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, paint);
        } else {
            if (scannerLaserBitmap == null) {
                scannerLaserBitmap = BitmapFactory.decodeResource(getResources(), scannerLaserResId);
            }
            if (scannerLaserBitmap != null) {
                int LaserHeight = scannerLaserBitmap.getHeight();
                if (scannerLaserTop < frame.top) {
                    scannerLaserTop = frame.top;
                    LASER_MOVE_DIRECTION = 1;
                }
                if (scannerLaserTop > frame.bottom - LaserHeight) {
                    scannerLaserTop = frame.bottom - LaserHeight;
                    LASER_MOVE_DIRECTION = -1;
                }
                Rect laserBitmapRect = new Rect(frame.left, scannerLaserTop, frame.right, scannerLaserTop + LaserHeight);
                canvas.drawBitmap(scannerLaserBitmap, null, laserBitmapRect, paint);
                scannerLaserTop = scannerLaserTop + LASER_MOVE_DISTANCE_PER_UNIT_TIME * LASER_MOVE_DIRECTION;
            }
        }
    }

    /**
     * 画出扫描边框
     */
    private void drawFrameBound(Canvas canvas, Rect frame) {
        if (scannerBoundWidth <= 0) return;
        paint.setColor(scannerBoundColor);
        canvas.drawRect(frame.left, frame.top, frame.right, frame.top + scannerBoundWidth, paint);       // top
        canvas.drawRect(frame.left, frame.top, frame.left + scannerBoundWidth, frame.bottom, paint);       // left
        canvas.drawRect(frame.right - scannerBoundWidth, frame.top, frame.right, frame.bottom, paint);      // right
        canvas.drawRect(frame.left, frame.bottom - scannerBoundWidth, frame.right, frame.bottom, paint);    // bottom
    }

    /**
     * 画扫描框边上的角，总共8个部分
     */
    private void drawFrameCorner(Canvas canvas, Rect frame) {
        if (scannerBoundCornerWidth <= 0 || scannerBoundCornerHeight <= 0) {
            return;
        }
        paint.setColor(scannerBoundCornerColor);
        // left top
        canvas.drawRect(frame.left - scannerBoundCornerWidth, frame.top - scannerBoundCornerWidth, frame.left + scannerBoundCornerHeight, frame.top, paint);
        canvas.drawRect(frame.left - scannerBoundCornerWidth, frame.top - scannerBoundCornerWidth, frame.left, frame.top + scannerBoundCornerHeight, paint);
        // left bottom
        canvas.drawRect(frame.left - scannerBoundCornerWidth, frame.bottom + scannerBoundCornerWidth - scannerBoundCornerHeight, frame.left, frame.bottom + scannerBoundCornerWidth, paint);
        canvas.drawRect(frame.left - scannerBoundCornerWidth, frame.bottom, frame.left - scannerBoundCornerWidth + scannerBoundCornerHeight, frame.bottom + scannerBoundCornerWidth, paint);
        // right top
        canvas.drawRect(frame.right + scannerBoundCornerWidth - scannerBoundCornerHeight, frame.top - scannerBoundCornerWidth, frame.right + scannerBoundCornerWidth, frame.top, paint);
        canvas.drawRect(frame.right, frame.top - scannerBoundCornerWidth, frame.right + scannerBoundCornerWidth, frame.top - scannerBoundCornerWidth + scannerBoundCornerHeight, paint);
        // right bottom
        canvas.drawRect(frame.right + scannerBoundCornerWidth - scannerBoundCornerHeight, frame.bottom, frame.right + scannerBoundCornerWidth, frame.bottom + scannerBoundCornerWidth, paint);
        canvas.drawRect(frame.right, frame.bottom + scannerBoundCornerWidth - scannerBoundCornerHeight, frame.right + scannerBoundCornerWidth, frame.bottom + scannerBoundCornerWidth, paint);
    }

    private void drawResultPoint(Canvas canvas, Rect frame, Size previewFrame) {
        float scaleX = frame.width() / (float) previewFrame.width;
        float scaleY = frame.height() / (float) previewFrame.height;
        List<ResultPoint> currentPossible = possibleResultPoints;
        List<ResultPoint> currentLast = lastPossibleResultPoints;
        int frameLeft = frame.left;
        int frameTop = frame.top;
        if (currentPossible.isEmpty()) {
            lastPossibleResultPoints = null;
        } else {
            possibleResultPoints = new ArrayList<>(5);
            lastPossibleResultPoints = currentPossible;
            paint.setAlpha(CURRENT_POINT_OPACITY);
            paint.setColor(resultPointColor);
            for (ResultPoint point : currentPossible) {
                canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX), frameTop + (int) (point.getY() * scaleY), POINT_SIZE, paint);
            }
        }
        if (currentLast != null) {
            paint.setAlpha(CURRENT_POINT_OPACITY / 2);
            paint.setColor(resultPointColor);
            float radius = POINT_SIZE / 2.0f;
            for (ResultPoint point : currentLast) {
                canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX), frameTop + (int) (point.getY() * scaleY), radius, paint);
            }
        }
    }
}

