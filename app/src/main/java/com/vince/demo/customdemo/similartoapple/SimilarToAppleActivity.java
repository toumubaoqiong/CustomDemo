package com.vince.demo.customdemo.similartoapple;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.vince.demo.customdemo.R;

import java.lang.reflect.Field;

public class SimilarToAppleActivity extends Activity implements View.OnTouchListener {

    ImageView _view;
    ViewGroup _root;
    private int _xDelta;
    private int _yDelta;
    private AnimationDrawable mAnimationDrawable;
    private int  screenWidth,screenHeight;
    private static int  statusBarHeight;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_to_apple);

        _root = (ViewGroup) findViewById(R.id.root);
        _view = (ImageView) findViewById(R.id.id_text);

        screenWidth = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        screenHeight = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                155, 160);
        layoutParams.leftMargin = screenWidth - layoutParams.width;
        layoutParams.topMargin = (screenHeight - getStatusBarHeight())/2  - layoutParams.height;

        _view.setLayoutParams(layoutParams);
        _view.setOnTouchListener(this);

        mAnimationDrawable = (AnimationDrawable) _view.getBackground();
        mAnimationDrawable.start();
    }

    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();
                layoutParams.leftMargin = X - _xDelta;
                layoutParams.topMargin = Y - _yDelta;

                if(layoutParams.leftMargin < 0){
                    layoutParams.leftMargin = 0;
                }
                if(layoutParams.leftMargin > screenWidth - layoutParams.width){
                    layoutParams.leftMargin = screenWidth - layoutParams.width;
                }

                if(layoutParams.topMargin <  0){
                    layoutParams.topMargin =  0;
                }

                if(layoutParams.topMargin > screenHeight - 250){
                    layoutParams.topMargin = screenHeight - 250;
                }

                view.setLayoutParams(layoutParams);
                break;
        }
        _root.invalidate();
        return true;
    }

    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    private int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }
}
