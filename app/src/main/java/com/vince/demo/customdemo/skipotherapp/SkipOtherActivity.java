package com.vince.demo.customdemo.skipotherapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.vince.demo.customdemo.R;

/**
 * 通过包名、类名进行跳转
 */
public class SkipOtherActivity extends Activity {

    private static final String TAG = "SkipOtherActivity";
    private static final String FROMMODEL = "fromModel";
    private static final String TYPE = "type";
    private static final String GRADE = "grade";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skip_other);
    }

    /**
     * 点击事件
     * @param v
     */
    public void onClick(View v){
        //通过包名、类名启动
       /* Intent intent = new Intent();
        intent.putExtra(FROMMODEL,"com.eebbk.getkey");
        intent.putExtra(GRADE,"八年级");
        intent.putExtra(TYPE,"名师视频精讲");
        intent.setComponent(new ComponentName("com.eebbk.share.android","com.eebbk.share.android.welcome.WelcomeActivity"));
        startActivity(intent);*/

        //通过Action启动
        Intent intent = new Intent("com.eebbk.share.android.welcome");
        startActivity(intent);

        Toast.makeText(this,"onClick",Toast.LENGTH_LONG).show();
    }
}
