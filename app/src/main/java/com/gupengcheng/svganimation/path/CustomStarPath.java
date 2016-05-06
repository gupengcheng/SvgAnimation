package com.gupengcheng.svganimation.path;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.gupengcheng.svganimation.svg.MathUtil;

/**
 * Created by pcgu on 16-5-3.
 */
public class CustomStarPath extends CustomPath {
    private float mPx;
    private float mPy;
    private float mRadius;
    private Path mStarPath;

    private int mStarColor = 0xffff4081;
    private int mStarStrokeColor = 0xff000000;
    private float mMaxAnimation = 1.2f;
    private Paint mStarPaint;
    private Paint mStarStrokePaint;
    private int mState = STATE_NOT_STARTED;
    private long mStartTime;

    public CustomStarPath(float px, float py, float radius, Path path) {
        mPx = px;
        mPy = py;
        mRadius = radius;
        mStarPath = path;

        initPaint();
    }

    private void initPaint() {
        mStarPaint = new Paint();
        mStarPaint.setStyle(Paint.Style.FILL);
        mStarPaint.setColor(mStarColor);
        mStarPaint.setAntiAlias(true);

        mStarStrokePaint = new Paint();
        mStarStrokePaint.setStyle(Paint.Style.STROKE);
        mStarStrokePaint.setColor(mStarStrokeColor);
        mStarStrokePaint.setAntiAlias(true);
    }

    @Override
    public void setFillPaintColor(int color) {

    }

    @Override
    public void setmScale(float scale) {
        mStarStrokePaint.setStrokeWidth(4 * scale);
    }

    @Override
    public void draw(Canvas canvas) {
        if (mState == STATE_NOT_STARTED) {
            return;
        }
        if (mState == STATE_FINISHED) {
            canvas.drawPath(mStarPath, mStarPaint);
        }
        long t = System.currentTimeMillis() - mStartTime;
        float phase = MathUtil.constrain(0, 1, t * 1f / 500);
        canvas.save();
        float scal = 1;
        if (mState == STATE_START_ENLARGE) {
            scal = 1 + phase * (mMaxAnimation - 1);
            scalePath(canvas, scal, mStarPaint);
            if (scal >= mMaxAnimation) {
                changeState(STATE_EXCUTE);
            }
        } else if (mState == STATE_START_REDUCE) {
            if (phase >= 1) {
                changeState(STATE_FINISHED);
            } else {
                scal = mMaxAnimation - phase * (mMaxAnimation - 1);
                scalePath(canvas, scal, mStarPaint);
            }
        } else if (mState == STATE_EXCUTE) {
            scal = mMaxAnimation;
            scalePath(canvas, scal, mStarPaint);
            changeState(STATE_START_REDUCE);
        } else {
            scal = 1;
            scalePath(canvas, scal, mStarPaint);
        }

        canvas.drawPath(mStarPath, mStarPaint);
        canvas.restore();
        canvas.save();
        // 用于去除锯齿
        canvas.translate(-(scal * mPx - mPx), -(scal * mPy - mPy));
        canvas.scale(scal, scal);
        canvas.drawPath(mStarPath, mStarPaint);
        canvas.restore();
    }

    @Override
    public void start() {
        if (mState != STATE_NOT_STARTED) {
            return;
        }
        changeState(STATE_START_ENLARGE);
        mStartTime = System.currentTimeMillis();
    }

    @Override
    public void reset() {
        changeState(STATE_NOT_STARTED);
        mStartTime = System.currentTimeMillis();
    }

    @Override
    public boolean isFinish() {
        if (mState == STATE_FINISHED) {
            return true;
        }
        return false;
    }

    private void scalePath(Canvas canvas, float scal, Paint mPaint) {
        canvas.translate(-(scal * mPx - mPx), -(scal * mPy - mPy));
        canvas.scale(scal, scal);
        canvas.drawPath(mStarPath, mPaint);
        canvas.clipPath(mStarPath);
    }

    private void changeState(int state) {
        if (mState == state) {
            return;
        }
        mStartTime = System.currentTimeMillis();
        mState = state;
    }
}
