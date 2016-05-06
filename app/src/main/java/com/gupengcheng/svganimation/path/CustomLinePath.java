package com.gupengcheng.svganimation.path;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;

/**
 * Created by pcgu on 16-5-3.
 */
public class CustomLinePath extends CustomPath {
    //开始时间
    private long mStartTime;
    PathMeasure mPathMeasure;
    float mScale = 1;
    Paint mFillPaint = new Paint();
    Paint mLinePaint = new Paint();
    private int mState = STATE_NOT_STARTED;
    private Path mLinePath;

    public static float SPEED = 0.1f;

    public CustomLinePath(Path path) {
        this.mLinePath = path;
        mPathMeasure = new PathMeasure(path, false);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setColor(Color.BLUE);
        mLinePaint.setAntiAlias(true);

        mFillPaint.setColor(Color.BLUE);
        mFillPaint.setAntiAlias(true);
    }

    protected Point getPoint(float distance) {
        Point point = null;
        if (distance > 0 && distance < mPathMeasure.getLength()) {
            point = new Point();
            float[] pos = new float[2];
            mPathMeasure.getPosTan(distance, pos, null);
            point.x = (int) pos[0];
            point.y = (int) pos[1];
        }
        return point;
    }

    public float length() {
        return mPathMeasure.getLength();
    }

    @Override
    public void draw(Canvas canvas) {
        if (mState == STATE_NOT_STARTED) {
            return;
        }
        if (mState == STATE_FINISHED) {
            mLinePaint.setPathEffect(null);
            canvas.drawPath(mLinePath, mLinePaint);
            return;
        }
        long t = System.currentTimeMillis() - mStartTime;

        float distance = SPEED * t;
        if (distance >= length()) {
            changeState(STATE_FINISHED);
        }
        Point point = getPoint(distance);
        if (point != null) {
            mLinePaint.setPathEffect(new DashPathEffect(
                    new float[]{
                            distance, length() - distance
                    }, 0));
            canvas.drawPath(mLinePath, mLinePaint);
            canvas.drawCircle(point.x, point.y, 10 * mScale, mFillPaint);
        }
    }

    @Override
    public void setmScale(float mScale) {
        this.mScale = mScale;
    }

    @Override
    public void setFillPaintColor(int color) {
        mLinePaint.setColor(color);
        mFillPaint.setColor(color);
    }

    private void changeState(int state) {
        if (mState == state) {
            return;
        }
        mStartTime = System.currentTimeMillis();
        mState = state;
    }

    @Override
    public void start() {
        if (mState != STATE_NOT_STARTED) {
            return;
        }
        changeState(STATE_EXCUTE);
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
}
