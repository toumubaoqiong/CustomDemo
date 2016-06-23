package com.vince.demo.customdemo.crash_handler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vince.demo.customdemo.R;

public class CrashReportActivity extends Activity {

    private Button mBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crash_report);

        mBtn.setVisibility(View.GONE);
    }
}
