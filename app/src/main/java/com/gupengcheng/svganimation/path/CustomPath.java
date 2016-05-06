package com.gupengcheng.svganimation.path;

import android.graphics.Canvas;

/**
 * Created by pcgu on 16-5-3.
 */
public abstract class CustomPath {
    // 未开始
    public static final int STATE_NOT_STARTED = 0;
    // 放大
    public static final int STATE_START_ENLARGE = 1;
    // 正在执行
    public static final int STATE_EXCUTE = 2;
    // 缩小
    public static final int STATE_START_REDUCE = 3;
    // 结束
    public static final int STATE_FINISHED = 4;

    public abstract void setFillPaintColor(int color);

    public abstract void setmScale(float scale);

    public abstract void draw(Canvas canvas);

    public abstract void start();

    public abstract void reset();

    public abstract boolean isFinish();
}
