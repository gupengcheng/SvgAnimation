package com.gupengcheng.svganimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gupengcheng.svganimation.svg.StarSvgView;

public class CustomSvgActivity extends AppCompatActivity{
    private StarSvgView mStarSvgView;
    private Button mHeartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_svg);
        initViews();
    }

    private void initViews() {
        mStarSvgView = (StarSvgView) findViewById(R.id.star_svg);
        mHeartButton = (Button) findViewById(R.id.start_btn);
        mHeartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStarSvgView.start();
            }
        });
    }

}
