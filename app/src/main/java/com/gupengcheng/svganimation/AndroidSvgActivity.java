package com.gupengcheng.svganimation;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by pcgu on 16-5-4.
 */
public class AndroidSvgActivity extends AppCompatActivity {
    private ImageView mAndroidIv;
    private ImageView mFavoriteIv;
    private Button mStartBtn;
    private static final int ANDROID_TO_APPLE = 1;
    private static final int APPLE_TO_ANDROID = 2;
    private int mCurrentAnimationType = ANDROID_TO_APPLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_svg);

        initViews();
    }

    private void initViews() {
        mAndroidIv = (ImageView) findViewById(R.id.image_android);
        mFavoriteIv = (ImageView) findViewById(R.id.image_favorite);
        mStartBtn = (Button) findViewById(R.id.start_btn);
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSvgAnimation();
            }
        });
    }

    private void startSvgAnimation() {
        if (mCurrentAnimationType == ANDROID_TO_APPLE) {
            mCurrentAnimationType = APPLE_TO_ANDROID;
            mAndroidIv.setImageResource(R.drawable.vector_drawable_ic_android_for_apple);
        } else {
            mCurrentAnimationType = ANDROID_TO_APPLE;
            mAndroidIv.setImageResource(R.drawable.vector_drawable_ic_apple_for_android);
        }
        Drawable drawable = mAndroidIv.getDrawable();
        if (drawable != null && drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }

        Drawable favoriteDrawable = mFavoriteIv.getDrawable();
        if (favoriteDrawable != null && favoriteDrawable instanceof Animatable) {
            ((Animatable) favoriteDrawable).start();
        }
    }

}
