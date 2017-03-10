package com.vince.demo.customdemo.webview_html.banner;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lhl on 16-4-26.
 */
public class WebViewJsAccess {
    public static final String INTENT_OPEN_BANNER_DATA = "INTENT_OPEN_BANNER_DATA";

    private static final String APP_STATE_RESUME = "resume";
    private static final String APP_STATE_PAUSE = "pause";

    private String netTag;
    private WebView webView;
    private Activity activity;
    private boolean hasWebViewExit;
    private String dataJson;

    private boolean isCanWebViewExit = false;

    private String httpJson;

    private Handler handler;

    private ClickBannerListener clickBannerListener;
    private WebViewJsAccessListener webViewJsAccessListener;

    /**
     * webview　js 访问
     *
     * @param webView
     * @param activity
     * @param hasWebViewExit 　是否存在webview退出功能
     */
    public WebViewJsAccess(WebView webView, Activity activity, boolean hasWebViewExit, String dataJson ) {
        this.webView = webView;
        this.activity = activity;
        this.hasWebViewExit = hasWebViewExit;
        this.dataJson = dataJson;

        if ( null != activity ) {
            netTag = activity.getClass().getName();
        }
        if ( null != webView ) {
            webView.getSettings().setJavaScriptEnabled( true );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                webView.getSettings().setMediaPlaybackRequiresUserGesture( false );
            }
            webView.addJavascriptInterface( new NativeJsApi(), "NativeJsApi" );
        }

        handler = new Handler() {
            @Override
            public void handleMessage( Message msg ) {
                super.handleMessage( msg );
                if ( 0 == msg.what ) {
                } else if ( 1 == msg.what ) {
//                    displayToast( (String) msg.obj );
                }
            }
        };
    }

    public void setClickBannerListener( ClickBannerListener listener ) {
        this.clickBannerListener = listener;
    }

    public void setWebViewJsAccessListener( WebViewJsAccessListener listener ) {
        this.webViewJsAccessListener = listener;
    }

    /**
     * 检查地址
     */
    private boolean checkURL( String url ) {
        if ( url != null ) {
            String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
            Pattern patt = Pattern.compile( regex );
            Matcher matcher = patt.matcher( url );
            boolean isMatch = matcher.matches();

            if ( !isMatch ) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private void JSONObject2HashMap(JSONObject jsonObject, Map< String, String> params ) {
        try {
            for (Iterator<String> keyStr = jsonObject.keys(); keyStr.hasNext();) {
                String key = keyStr.next().trim();
                params.put( key, (String)jsonObject.get( key ) );
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 网络请求
     *
     * @param jsonStr
     */
    private void getNormal( String jsonStr ) {
        /*L.d( "laihuilang=getNormal=" + jsonStr );
        httpJson = jsonStr;
        if ( TextUtils.isEmpty( httpJson ) || null == webView ) {
            return;
        }

        try {
            WebViewJsHttp webViewJsHttp = JSON.parseObject( httpJson, WebViewJsHttp.class );
            HashMap< String, String > params = new HashMap<>();
            params.put(NetConstant.USER_ID, AppManager.getUserId());
            params.put(NetConstant.ACCOUNT_ID, AppManager.getAccountId());
            JSONObject2HashMap( new JSONObject( webViewJsHttp.getParams() ), params );
            if ( WebViewJsHttp.HTTP_GET.equals( webViewJsHttp.getType() ) ) {
                NetWorkRequest.getInstance( activity ).getNormal( webViewJsHttp.getRequestUrl(), false, params, netTag,
                        new NetRequestListener<String>() {
                            @Override
                            public void onSuccess( String response ) {
                                webView.loadUrl( "javascript:NativeJsApi.httpReturn('" + httpJson + "','" + response + "')" );
                            }

                            @Override
                            public void onFailed( String message ) {
                                webView.loadUrl( "javascript:NativeJsApi.httpReturn('" + httpJson + "','" + message + "')" );
                            }
                        } );
            } else if ( WebViewJsHttp.HTTP_POST.equals( webViewJsHttp.getType() ) ) {
                NetWorkRequest.getInstance( activity ).postNormal( webViewJsHttp.getRequestUrl(), false, params, netTag,
                        new NetRequestListener<String>() {
                            @Override
                            public void onSuccess( String response ) {
                                webView.loadUrl( "javascript:NativeJsApi.httpReturn('" + httpJson + "','" + response + "')" );
                            }

                            @Override
                            public void onFailed( String message ) {
                                webView.loadUrl( "javascript:NativeJsApi.httpReturn('" + httpJson + "','" + message + "')" );
                            }
                        } );
            }

        } catch ( Exception e ) {
            L.e( e );
        }*/
    }

    /**
     * 点击时间埋点
     *
     * @param jsonStr
     */
    private void clickEvent( String jsonStr ) {

    }

    /**
     * 返回键处理
     */
    public boolean onBackPressed() {
        if ( null == webView || !isCanWebViewExit ) {
            return false;
        }
        webView.loadUrl( "javascript:NativeJsApi.backbutton()" );
        return true;
    }

    /**
     * 退出取消网络请求
     */
    public void onDestroy() {
        if ( null == activity ) {
            return;
        }
    }

    /**
     * 暂停
     */
    public void onPause() {
        appState( WebViewJsAccess.APP_STATE_PAUSE );
    }

    /**
     * 暂停
     */
    public void onResume() {
        appState( WebViewJsAccess.APP_STATE_RESUME );
    }

    /**
     * app挂起和激活处理
     */
    private void appState( String state ) {
        if ( null == webView ) {
            return;
        }
        webView.loadUrl( "javascript:NativeJsApi.appState('" + state + "')" );
    }

    /**
     * 界面退出
     */
    private void activityExit() {
        if ( !hasWebViewExit || null == activity ) {
            return;
        }
        activity.finish();
    }

    /**
     * 显示toast
     */
    private void displayToast( String content ) {
        if ( null == activity ) {
            return;
        }
//        T.getInstance(activity).s( content );
    }

    /**
     * 网络监听变化
     * @param isWifiOn
     * @param isMobileOn
     */
    public void onNetWorkConnectChanged( boolean isWifiOn, boolean isMobileOn ) {
        if ( null == webView ) {
            return;
        }
        String state = "offline";
        if ( isWifiOn ) {
            state = "wifi";
        } else if ( isMobileOn ) {
            state = "mobile";
        }
        webView.loadUrl( "javascript:NativeJsApi.online('" + state + "')" );
    }

    private void enterOtherWebViewActivity( String jsonStr ) {
       /* try {
            if ( activity instanceof WebViewActivity) {
                return ;
            }
            L.d( "laihuilang=pageEnd=" + jsonStr );
            OpenBannerData openBannerData = JSON.parseObject( jsonStr, OpenBannerData.class );
            Intent intent = new Intent();
            intent.putExtra( INTENT_OPEN_BANNER_DATA, openBannerData );
            intent.setClass( activity, WebViewActivity.class );
            activity.startActivity( intent );
        } catch ( Exception e ) {
            L.e( e );
        }*/
    }

    private String getPersonalInfo() {
      /*  Account account = AppManager.getUserAccount();
        String userInfoJson = com.alibaba.fastjson.JSONObject.toJSONString( account );
        L.d( "userInfoJson=" + userInfoJson );
        return userInfoJson;*/
        return "";
    }

    private String getInternetState() {
        String state = "offline";

        state = "wifi";

       return state;
    }

    private class NativeJsApi {
        @JavascriptInterface
        public void openBannerDetail( String clickUrl ) {
            Message msg = new Message();
            msg.what = 0;
            msg.obj = clickUrl;
            handler.sendMessageDelayed( msg, 1 );
        }

        @JavascriptInterface
        public void http( String jsonStr ) {
            getNormal( jsonStr );
        }

        @JavascriptInterface
        public void clickEventDA( String jsonStr ) {
            clickEvent( jsonStr );
        }

        @JavascriptInterface
        public void pageBeginDA( String jsonStr ) {
//            pageBegin( jsonStr );
        }

        @JavascriptInterface
        public void pageEndDA( String jsonStr ) {
//            pageEnd( jsonStr );
        }

        @JavascriptInterface
        public void webviewExit() {
            activityExit();
        }

        @JavascriptInterface
        public void showToast( String content ) {
            Message msg = new Message();
            msg.what = 1;
            msg.obj = content;
            handler.sendMessageDelayed( msg, 1 );
        }

        @JavascriptInterface
        public void ready() {
            isCanWebViewExit = true;
            if ( null != webViewJsAccessListener ) {
                webViewJsAccessListener.onReady();
            }
        }

        @JavascriptInterface
        public void openWebview( String jsonStr ) {
            enterOtherWebViewActivity( jsonStr );
        }

        @JavascriptInterface
        public String getUserInfo() {
            return getPersonalInfo();
        }

        @JavascriptInterface
        public String getData() {
            return dataJson;
        }

        @JavascriptInterface
        public String getOnlineState() {
            return getInternetState();
        }
    }
}
