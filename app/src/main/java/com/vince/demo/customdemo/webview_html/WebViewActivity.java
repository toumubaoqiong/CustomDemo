package com.vince.demo.customdemo.webview_html;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.widget.TextView;
import android.widget.Toast;

import com.vince.demo.customdemo.R;

import java.io.InputStream;

public class WebViewActivity extends Activity {

    private BaseWebView Wv;
    private TextView myTextView;
    final Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Wv = (BaseWebView) findViewById(R.id.webView1);
        myTextView = (TextView) findViewById(R.id.textView1);
        final JavaScriptInterface myJavaScriptInterface
                = new JavaScriptInterface(this);

        Wv.getSettings().setJavaScriptEnabled(true);
        Wv.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放

        Wv.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");
        String html = getFromAssets(this, "index2.html");
        Wv.loadDataWithBaseURL( "file:///android_asset/",html, "text/html",
                "utf-8", null );
    }


    public class JavaScriptInterface {
        Context mContext;

        JavaScriptInterface(Context c) {
            mContext = c;
        }
        @JavascriptInterface
        public void showToast(String webMessage) {
            final String msgeToast = webMessage;
            myHandler.post(new Runnable() {
                @Override
                public void run() {
                    // This gets executed on the UI thread so it can safely modify Views
                    myTextView.setText(msgeToast);
                }
            });

            Toast.makeText(mContext, webMessage, Toast.LENGTH_SHORT).show();
        }
    }

    public static String getFromAssets(Context context, String fileName) {
        String result;
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            //获取文件的字节数
            int lenght = in.available();
            //创建byte数组
            byte[]  buffer = new byte[lenght];
            //将文件中的数据读到byte数组中
            in.read(buffer);
            result = new String(buffer,"utf-8");
            in.close();
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }
}
