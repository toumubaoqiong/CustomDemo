package com.vince.demo.customdemo.loading;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vince.demo.customdemo.R;


/**
 * 统一loading框，无任何功能
 * @author dr
 *
 */
public class MyLoadingView extends RelativeLayout {
	
	private TextView tvMessage ;

	public MyLoadingView(Context context) {
		super(context);
		init(context);

	}

	public MyLoadingView(Context context, AttributeSet attrs){
		super(context, attrs);
		init(context);
	}

	public MyLoadingView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		init(context);
	}

	/**
	 * init view and layout
	 * @param ctx
	 */
	private void init(Context ctx){
		LayoutInflater.from(ctx).inflate(R.layout.comment_dlg_wait, this) ;
		tvMessage = (TextView) findViewById(R.id.wait_hint_txt) ;
	}

	/**
	 * set message from a string
	 * @param str
	 */
	public void setMessage(String str){
		tvMessage.setText(str);
	}

	/**
	 * set message from resouce
	 * @param resouceId
	 */
	public void setMessage(int resouceId){
		tvMessage.setText(resouceId);
	}
}
