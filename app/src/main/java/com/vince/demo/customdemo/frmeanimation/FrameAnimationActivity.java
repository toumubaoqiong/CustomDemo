package com.vince.demo.customdemo.frmeanimation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.vince.demo.customdemo.R;

public class FrameAnimationActivity
        extends Activity
{

    private int[] pFrameRess = {R.mipmap.scattering_flowers_one,
                                R.mipmap.scattering_flowers_two,
                                R.mipmap.scattering_flowers_three,
                                R.mipmap.scattering_flowers_four,
                                R.mipmap.scattering_flowers_five,
                                R.mipmap.scattering_flowers_six,
                                R.mipmap.scattering_flowers_seven,
                                R.mipmap.scattering_flowers_eight};

    private SceneAnimation mSceneAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation);

        ImageView scatteringFlowersIv = (ImageView)findViewById(R.id.sign_light_img);
        mSceneAnimation = new SceneAnimation(scatteringFlowersIv, pFrameRess,100);
    }
}
