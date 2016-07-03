package com.vince.demo.customdemo.viewfipper;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.vince.demo.customdemo.R;

/**
 * decription ：
 * author ： zhua
 * Created at 2016/7/3.
 */
public class MyOnGestureListener implements GestureDetector.OnGestureListener {

    private static final String TAG = "MyOnGestureListener";

    private ViewFlipper mViewFlipper;
    private Context mContext;
    private ImageView mImageViewFir;
    private ImageView mImageViewSec;
    private ImageView mImageViewtThi;
    private ImageView mImageViewFou;
    private int mCurPage = 1;

    public void setmImageViewFir(ImageView mImageViewFir) {
        this.mImageViewFir = mImageViewFir;
    }

    public void setmImageViewSec(ImageView mImageViewSec) {
        this.mImageViewSec = mImageViewSec;
    }

    public void setmImageViewtThi(ImageView mImageViewtThi) {
        this.mImageViewtThi = mImageViewtThi;
    }

    public void setmImageViewFou(ImageView mImageViewFou) {
        this.mImageViewFou = mImageViewFou;
    }

    MyOnGestureListener(Context context,ViewFlipper viewFlipper){
        mViewFlipper = viewFlipper;
        mContext = context;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i(TAG, "e1="+e1.getX()+" e2="+e2.getX()+" e1-e2="+(e1.getX()-e2.getX()));

        if(e1.getX()-e2.getX()>120){
            mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
            mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));
            mCurPage += 1;
            if(mCurPage > 4){
                mCurPage = 1;
            }

            setSelectedPage(mCurPage);
            mViewFlipper.showNext();
            return true;
        }else if(e1.getX()-e2.getX()<-120){
            mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_in));
            mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_out));
            mViewFlipper.showPrevious();
            mCurPage -= 1;
            if(mCurPage == 0){
                mCurPage = 4;
            }
            
            setSelectedPage(mCurPage);
            return true;
        }

        return false;
    }

    /**
     * 设置dot
     */
    public void setSelectedPage(int curPage){

        Log.i(TAG, "setSelectedPage: " + curPage);
        switch (curPage){
            case 1:
                mImageViewFir.setImageResource(R.mipmap.dot2);
                mImageViewSec.setImageResource(R.mipmap.dot1);
                mImageViewtThi.setImageResource(R.mipmap.dot1);
                mImageViewFou.setImageResource(R.mipmap.dot1);
                break;
            case 2:
                mImageViewFir.setImageResource(R.mipmap.dot1);
                mImageViewSec.setImageResource(R.mipmap.dot2);
                mImageViewtThi.setImageResource(R.mipmap.dot1);
                mImageViewFou.setImageResource(R.mipmap.dot1);
                break;
            case 3:
                mImageViewFir.setImageResource(R.mipmap.dot1);
                mImageViewSec.setImageResource(R.mipmap.dot1);
                mImageViewtThi.setImageResource(R.mipmap.dot2);
                mImageViewFou.setImageResource(R.mipmap.dot1);
                break;
            case 4:
                mImageViewFir.setImageResource(R.mipmap.dot1);
                mImageViewSec.setImageResource(R.mipmap.dot1);
                mImageViewtThi.setImageResource(R.mipmap.dot1);
                mImageViewFou.setImageResource(R.mipmap.dot2);
                break;
        }
    }
}
