package com.vince.demo.customdemo.application;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.vince.demo.customdemo.R;
import com.vince.demo.customdemo.crash_handler.MyApplication;

public class SecondMainActivity
        extends Activity
{
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_main);

        mTextView = (TextView)findViewById(R.id.TextView_tip);
        MyApplication myApplication = (MyApplication)getApplication();
        mTextView.setText(myApplication.getContent().toUpperCase());
    }
}
