package com.vince.demo.customdemo.marqueeTextView;

import android.app.Activity;
import android.os.Bundle;
import com.vince.demo.customdemo.R;

public class MarqueeTextViewActivity extends Activity {

    private MarqueeTextView mMqTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marquee_text_view);

//        mMqTv = (MarqueeTextView)findViewById(R.id.AMTV1);
//        mMqTv.setSelected(true);
    }
}
