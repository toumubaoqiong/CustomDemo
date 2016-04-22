package com.vince.demo.customdemo.net_set;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.vince.demo.customdemo.R;

/**
 *description:打开网络设置界面
 *author:vince
 */
public class NetworkSettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_setting);
    }

    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
        startActivity(intent);
    }
}
