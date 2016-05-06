package com.gupengcheng.svganimation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by pcgu on 16-5-4.
 */
public class HomeActivity extends AppCompatActivity {
    private Button mCustomSvgViewBtn;
    private Button mAndroidSvgAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
    }

    private void initViews() {
        mCustomSvgViewBtn = (Button) findViewById(R.id.custom_svg_view);
        mAndroidSvgAnimation = (Button) findViewById(R.id.android_svg_animation);

        mCustomSvgViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CustomSvgActivity.class);
                startActivity(intent);
            }
        });

        mAndroidSvgAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AndroidSvgActivity.class);
                startActivity(intent);
            }
        });
    }
}
