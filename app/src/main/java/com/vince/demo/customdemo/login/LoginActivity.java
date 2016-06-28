package com.vince.demo.customdemo.login;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.widget.EditText;

import com.vince.demo.customdemo.R;

/**
 * description:模拟登陆界面
 * 1、点击editText时，随着弹出输入法，整体向上移动
 * 2、editText输入监听
 * author:vince
 */
public class LoginActivity extends Activity {

    private EditText usernameET;
    private EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //代码去掉标题栏，要实现输入法弹出整体上移，要在代码级去掉标题栏
        setContentView(R.layout.activity_login);

        usernameET.addTextChangedListener(textWatcher);
        passwordET.addTextChangedListener(textWatcher);
    }

    /*
	 * 文本改变监听事件
	 */
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }
        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
