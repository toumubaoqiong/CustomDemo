package com.vince.demo.customdemo.webview_html;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.eebbk.html5.webview.BBKWebViewCilent;

/**
 * Created by Administrator on 2016/5/11.
 */
public class BaseWebView
        extends WebView
{

    public BaseWebView(Context context) {
        super(context);
        setWebView(this);
    }
    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWebView(this);
    }

    private void setWebView(WebView webView) {
        webView.setOnLongClickListener( new OnLongClickListenerM() );
        // 禁用输入框获取焦点弹出键盘
//        webView.setFocusable(false);
//        webView.setHorizontalScrollBarEnabled(false);
//        webView.setVerticalScrollBarEnabled(false);
//        webView.setScrollContainer(false);
        WebSettings webSettings = webView.getSettings();
//        webSettings.setAllowContentAccess(true);
        //先启用JavaScript
        webSettings.setJavaScriptEnabled(true);
        //不允许缩放
   //     webSettings.setSupportZoom(false);
  //      webSettings.setBuiltInZoomControls(false);
//      宽度不超过屏幕宽度
 //       webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
//        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
//        webSettings.setDefaultFontSize(16);
 //       webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        //设置默认缩放为不缩放
        webView.setInitialScale(100);
        webView.setWebViewClient(new BBKWebViewCilent());
        /** webView 调试 */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setWebContentsDebuggingEnabled(true);
        }

    }


    //禁止水平滚动,  水平的scroll永远置为0
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (l != 0){
            scrollTo(0, t);
        }
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
//        return super.canScrollHorizontally(direction);
        return false;
    }

    private static class OnLongClickListenerM implements OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            return true;
        }
    }
}
