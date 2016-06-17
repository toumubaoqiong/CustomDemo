package com.vince.demo.customdemo.window_background;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * decription ：
 * author ： zhua
 * Created at 2016/5/31.
 */
public class FpsImageView extends ImageView {
    private long mStartTime = -1;
    private int mCounter;
    private int mFps;
    private final Paint mPaint;

    public FpsImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setColor(0xFFFFFFFF);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas) {
        if (mStartTime == -1) {
            mStartTime = SystemClock.elapsedRealtime();
            mCounter = 0;
        }
        long now = SystemClock.elapsedRealtime();
        long delay = now - mStartTime;
        super.draw(canvas);

        canvas.drawText(mFps + " fps", 100, 50, mPaint);
        if (delay > 1000L) {
            mStartTime = now;
            mFps = mCounter;
            mCounter = 0;
        }

        mCounter++;
    }
}
