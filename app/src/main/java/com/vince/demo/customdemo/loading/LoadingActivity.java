package com.vince.demo.customdemo.loading;

import android.app.Activity;
import android.os.Bundle;
import com.vince.demo.customdemo.R;

public class LoadingActivity extends Activity {

    private MyLoadingView mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

//        initView();

    }

    public void initView(){
        mLoadingView = (MyLoadingView) findViewById(R.id.LoadingView);
    }
}
