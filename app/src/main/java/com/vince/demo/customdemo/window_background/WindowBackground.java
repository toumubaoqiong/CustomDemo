package com.vince.demo.customdemo.window_background;

import android.app.Activity;
import android.os.Bundle;

import com.vince.demo.customdemo.R;

public class WindowBackground extends Activity {

    private FpsImageView mFpsImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_background);

        mFpsImageView = (FpsImageView)findViewById(R.id.FpsImageView);

        mFpsImageView.postInvalidate();
    }
}
