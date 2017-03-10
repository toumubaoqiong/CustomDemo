package com.vince.demo.customdemo.webview_html;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.eebbk.html5.webview.BBKWebViewCilent;
import com.vince.demo.customdemo.R;
import com.vince.demo.customdemo.webview_html.banner.CustomWebView;
import com.vince.demo.customdemo.webview_html.banner.WebViewJsAccess;

/**
 * 模拟banner数据，用于定位平板首页闪退问题
 */
public class TestActivity extends Activity {

    private CustomWebView bannerView;
    private WebSettings webSettings;
    private boolean isPageFinish = false;
    private int webViewLoadStatus = 0;
    private WebViewJsAccess webViewJsAccess;
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_PROGRESS);

        setContentView(R.layout.activity_test);

//        initView();
        init();
    }

    /**
     * 初始化View
     */
    public void initView(){
        bannerView = ( CustomWebView ) findViewById( R.id.my_plan_banner_view_id );
        bannerView.setFocusable(false);

        if (null != webSettings) {
            return;
        }

        webSettings = bannerView.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        bannerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        bannerView.setInitialScale(100);

        bannerView.setWebViewClient(new BBKWebViewCilent() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (null == bannerView) {
                    return;
                }
                isPageFinish = true;
                if (0 != webViewLoadStatus) {
                    bannerView.setVisibility(View.GONE);
                    webViewLoadStatus = 0;
                } else {
                    bannerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                if (!isPageFinish) {
                        webViewLoadStatus = errorCode;
                }
            }
        });

        String bannerData = "{\"portalPojos\":[{\"clickUrl\":\"{\\\"dataType\\\":\\\"HTML\\\",\\\"internetUrl\\\":\\\"http://actcdn.eebbk.com/act-msfd/20161221/c_index_974ca4c.html\\\"}\",\"content\":\"<img class=\\\"openBannerDetail\\\" src=\\\"http://file.eebbk.net/server-socialadmin/cloudIDN/social_admin/2016/12/21/110541513_65b3c5b7833186de.png\\\"/>\",\"description\":\"名师辅(平板端)\",\"endDate\":\"1514687143000\",\"id\":213,\"startDate\":\"1470363946000\",\"title\":\"新课程上线活动\"}],\"url\":\"http://actcdn.eebbk.com/act-msfd/portal/c_banner_1df646f.html\"}";

        bannerView.loadUrl("http://actcdn.eebbk.com/act-msfd/portal/c_banner_1df646f.html");
        webViewJsAccess = new WebViewJsAccess(bannerView, this, false, bannerData);
    }

    /**
     *初始化
     */
    public void init(){
        webview = (WebView)findViewById(R.id.WebView_test);

        webview.getSettings().setJavaScriptEnabled(true);

        final Activity activity = this;
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                activity.setProgress(progress * 1000);
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });

        webview.loadUrl("https://www.baidu.com/");
    }



}
