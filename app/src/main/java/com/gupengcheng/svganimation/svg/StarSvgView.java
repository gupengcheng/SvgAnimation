package com.gupengcheng.svganimation.svg;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.gupengcheng.svganimation.R;
import com.gupengcheng.svganimation.path.CustomLinePath;
import com.gupengcheng.svganimation.path.CustomPath;
import com.gupengcheng.svganimation.path.CustomStarPath;
import com.gupengcheng.svganimation.path.SVGAnimationPath;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gupengcheng on 16/5/2.
 */
public class StarSvgView extends View {
    private static final String TAG = "StarSvgView";
    private SVG svg;

    //svg图片的缩放比
    private float scale;

    //缩放之后的偏移量
    private float mTransferX;
    private float mTransferY;

    // 要绘制的路径数组
    private List<CustomPath> myLinePaths = new ArrayList<CustomPath>();

    public int fillColor = 0xff6a9ebb;

    //动画运行的状态
    public static final int STATE_NOT_STARTED = 0;
    public static final int STATE_STARTED = 1;

    private int mState = STATE_NOT_STARTED;

    public StarSvgView(Context context) {
        super(context);
        init();
    }

    public StarSvgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StarSvgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public StarSvgView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        try {
            //关闭硬件加速器
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            AssetManager assets = getContext().getAssets();
            svg = SVGParser.getSVGFromAsset(assets, "svg/star.svg");
        } catch (SVGParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int svgWidth = 720;
        int svgHeight = 1120;
        float scaleW = w * 1.0f / svgWidth;
        float scaleH = h * 1.0f / svgHeight;
        scale = scaleH > scaleW ? scaleW : scaleH;
        mTransferX = (w - svgWidth * scale) / 2;
        mTransferY = (h - svgHeight * scale) / 2;
        rebuildPath();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.svg_bg));
        canvas.translate(mTransferX, mTransferY);
        if (svg != null) {
            canvas.save();
            canvas.scale(scale, scale);
            svg.getPicture().draw(canvas);
            canvas.restore();
        }

        if (mState == STATE_NOT_STARTED) {
            return;
        }

        for (int i = 0; i < myLinePaths.size(); i++) {
            CustomPath linePath = myLinePaths.get(i);
            linePath.draw(canvas);
            if (!linePath.isFinish()) {
                linePath.start();
                break;
            }
        }

        if (!isEndAnimation()) {
            ViewCompat.postInvalidateOnAnimation(this);
        } else {
            if (myLinePaths.size() > 0) {
                myLinePaths.get(myLinePaths.size() - 1).draw(canvas);
            }
        }
    }

    private void rebuildPath() {
        SvgPathParser parser = new SvgPathParser() {

            @Override
            protected float transformX(float x) {
                return x * scale;
            }

            @Override
            protected float transformY(float y) {
                return y * scale;
            }
        };
        try {
            //Star1
            CustomPath shapPath = new CustomStarPath(162 * scale, 185 * scale, 113 * scale,
                    parser.parsePath(SVGAnimationPath.STAR_1));
            shapPath.setmScale(scale);
            myLinePaths.add(shapPath);

            //到Star2的直线
            CustomLinePath linePath = new CustomLinePath(
                    parser.parsePath(SVGAnimationPath.TO_STAR_2_PATH));
            CustomLinePath.SPEED = linePath.length() / 600;
            linePath.setmScale(scale);
            linePath.setFillPaintColor(fillColor);
            myLinePaths.add(linePath);

            //Star2
            shapPath = new CustomStarPath(463 * scale, 265 * scale, 101 * scale,
                    parser.parsePath(SVGAnimationPath.STAR_2));
            shapPath.setmScale(scale);
            myLinePaths.add(shapPath);

            //到Star3的直线
            linePath = new CustomLinePath(
                    parser.parsePath(SVGAnimationPath.TO_STAR_3_PATH));
            linePath.setmScale(scale);
            linePath.setFillPaintColor(fillColor);
            myLinePaths.add(linePath);


            //Star3
            shapPath = new CustomStarPath(235 * scale, 518 * scale, 120 * scale,
                    parser.parsePath(SVGAnimationPath.STAR_3));
            shapPath.setmScale(scale);
            myLinePaths.add(shapPath);

            //到Star4的直线
            linePath = new CustomLinePath(
                    parser.parsePath(SVGAnimationPath.TO_STAR_4_PATH));
            linePath.setmScale(scale);
            linePath.setFillPaintColor(fillColor);
            myLinePaths.add(linePath);


            //Star4
            shapPath = new CustomStarPath(551 * scale, 575 * scale, 113 * scale,
                    parser.parsePath(SVGAnimationPath.STAR_4));
            shapPath.setmScale(scale);
            myLinePaths.add(shapPath);

            //到Star5的直线
            linePath = new CustomLinePath(
                    parser.parsePath(SVGAnimationPath.TO_STAR_5_PATH));
            linePath.setmScale(scale);
            linePath.setFillPaintColor(fillColor);
            myLinePaths.add(linePath);

            //Star5
            shapPath = new CustomStarPath(405 * scale, 780 * scale, 113 * scale,
                    parser.parsePath(SVGAnimationPath.STAR_5));
            shapPath.setmScale(scale);
            myLinePaths.add(shapPath);

            //到Star6的直线
            linePath = new CustomLinePath(
                    parser.parsePath(SVGAnimationPath.TO_STAR_6_PATH));
            linePath.setmScale(scale);
            linePath.setFillPaintColor(fillColor);
            myLinePaths.add(linePath);

            //Star6
            shapPath = new CustomStarPath(163 * scale, 995 * scale, 121 * scale,
                    parser.parsePath(SVGAnimationPath.STAR_6));
            shapPath.setmScale(scale);
            myLinePaths.add(shapPath);

            //到Star7的直线
            linePath = new CustomLinePath(
                    parser.parsePath(SVGAnimationPath.TO_STAR_7_PATH));
            linePath.setmScale(scale);
            linePath.setFillPaintColor(fillColor);
            myLinePaths.add(linePath);

            //Star7
            shapPath = new CustomStarPath(527 * scale, 1016 * scale, 101 * scale,
                    parser.parsePath(SVGAnimationPath.STAR_7));
            shapPath.setmScale(scale);
            myLinePaths.add(shapPath);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private boolean isEndAnimation() {
        for (int i = 0; i < myLinePaths.size(); i++) {
            CustomPath myPath = myLinePaths.get(i);
            if (!myPath.isFinish()) {
                return false;
            }
        }
        return true;
    }

    public void start() {
        changeState(STATE_STARTED);
        for (int i = 0; i < myLinePaths.size(); i++) {
            myLinePaths.get(i).reset();
        }
        ViewCompat.postInvalidateOnAnimation(this);
    }

    private void changeState(int state) {
        if (mState == state) {
            return;
        }
        mState = state;
    }
}
