package com.vince.demo.customdemo.controls;

import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.widget.TextView;

import com.vince.demo.customdemo.R;

/**
 * description:TextView 扩展使用   1、单个textview 中显示多种style  2、textview  显示  %x% 形式
 * author:vince
 * creator at:2016/9/27
 */
public class ExpandTextViewActivity extends Activity {

    private TextView mTxtOne;
    private TextView mTxtTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_text_view);

        initView();
    }

    private void initView() {

        mTxtOne = (TextView) findViewById(R.id.TextView_one);
        mTxtTwo = (TextView) findViewById(R.id.TextView_two);

        //单个textview 中显示多种style
        String content = "hellow" + "world!";
        int lenRate = content.length();
        SpannableString rate = new SpannableString(content);
        rate.setSpan(new TextAppearanceSpan(this, R.style.item_rate_text_style1), 0, lenRate - 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        rate.setSpan(new TextAppearanceSpan(this, R.style.item_rate_text_style2), lenRate - 6, lenRate, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTxtOne.setText(rate);

        //textview  显示  %x% 形式
        String data = getResources().getString(R.string.data);
        data = String.format(data, 100, 10.3, "2011-07-01");

        mTxtTwo.setText(data);
    }

}
