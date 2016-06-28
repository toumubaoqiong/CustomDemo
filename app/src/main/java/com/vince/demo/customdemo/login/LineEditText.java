package com.vince.demo.customdemo.login;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

public class LineEditText extends EditText {
	private Paint paint;

	/**
	 * @param context
	 * @param attrs
	 */
	public LineEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		paint = new Paint();
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2);
		if (this.isFocused() == true) {
			paint.setColor(Color.parseColor("#1B8257"));
		} else {
			paint.setColor(Color.parseColor("#CCCCCC"));
		}
		// ������
		canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1,
				this.getHeight() - 1, paint);
	}
}
