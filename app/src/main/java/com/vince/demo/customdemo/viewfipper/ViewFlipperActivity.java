package com.vince.demo.customdemo.viewfipper;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.vince.demo.customdemo.R;

/**
 * ViewFlipper实现滑动效果
 */
public class ViewFlipperActivity extends Activity {

    private static final String TAG = "ViewFlipperActivity";
    
    private ViewFlipper mViewFlipper;
    private GestureDetector mGestureDetector;
    private ImageView mImageViewFir;
    private ImageView mImageViewSec;
    private ImageView mImageViewtThi;
    private ImageView mImageViewFou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view_flipper);

        init();
    }

    public void init() {
        mViewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        mImageViewFir = (ImageView) findViewById(R.id.ImageView_fir);
        mImageViewSec = (ImageView) findViewById(R.id.ImageView_sec);
        mImageViewtThi = (ImageView) findViewById(R.id.ImageView_thi);
        mImageViewFou = (ImageView) findViewById(R.id.ImageView_fou);

        MyOnGestureListener myOnGestureListener = new MyOnGestureListener(this,mViewFlipper);
        myOnGestureListener.setmImageViewFir(mImageViewFir);
        myOnGestureListener.setmImageViewSec(mImageViewSec);
        myOnGestureListener.setmImageViewtThi(mImageViewtThi);
        myOnGestureListener.setmImageViewFou(mImageViewFou);
        mGestureDetector = new GestureDetector(this, myOnGestureListener);

        mViewFlipper.addView(getImageView(R.mipmap.desert));
        mViewFlipper.addView(getImageView(R.mipmap.hydrangeas));
        mViewFlipper.addView(getImageView(R.mipmap.jellyfish));
        mViewFlipper.addView(getImageView(R.mipmap.koala));
    }

    /**
     * 获取视图控件
     *
     * @param id
     * @return
     */
    private ImageView getImageView(int id) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(id);
        return imageView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return mGestureDetector.onTouchEvent(event);
    }
}
