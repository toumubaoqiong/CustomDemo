package com.vince.demo.customdemo.jni;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.vince.demo.customdemo.R;

public class JniTestActivity extends AppCompatActivity {
    static {
        System.loadLibrary("jnidemo");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni_test);

        ((TextView)findViewById(R.id.name_text)).setText(JniDemo.getStringFromNative());
    }
}