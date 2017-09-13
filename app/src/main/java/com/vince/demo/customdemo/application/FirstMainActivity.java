package com.vince.demo.customdemo.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vince.demo.customdemo.R;
import com.vince.demo.customdemo.crash_handler.MyApplication;

public class FirstMainActivity extends Activity{
    private EditText mEditText;
    private Button   mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main);

        mEditText = (EditText)findViewById(R.id.EditText_content);
        mButton = (Button)findViewById(R.id.Button_save);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication app = (MyApplication)getApplication();
                app.setContent("zhua");

                Intent intent = new Intent(FirstMainActivity.this, SecondMainActivity.class);
                startActivity(intent);
            }
        });
    }


}
