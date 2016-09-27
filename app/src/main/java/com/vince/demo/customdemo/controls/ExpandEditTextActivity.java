package com.vince.demo.customdemo.controls;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.EditText;

import com.vince.demo.customdemo.R;
/**
 *description:扩展EditText 功能    监听输入字体数达到最大长度时给予相应的提示
 *author:Vince
 *creator at:2016/9/27
 */
public class ExpandEditTextActivity extends Activity {

    private EditText mEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_edit_text);

        mEdt = (EditText)findViewById(R.id.EditText_message);
        mEdt.setFilters(new InputFilter[] { new LengthFilter(this,50) });
    }
}
