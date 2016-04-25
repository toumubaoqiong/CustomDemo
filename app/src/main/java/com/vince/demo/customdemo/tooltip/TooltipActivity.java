package com.vince.demo.customdemo.tooltip;

import android.app.DownloadManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Display;
import android.view.Window;

import com.vince.demo.customdemo.R;

/**
 * @author  vince
 * @des  自定义悬浮控件，基于activity
 */
public class TooltipActivity extends Activity {

    private int screenW;        //屏幕宽度
    private int screenH;        //屏幕高度
    private DragTV dragTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_tooltip);

        Display dis = this.getWindowManager().getDefaultDisplay();
        // 获取屏幕宽度
        screenW = dis.getWidth();
        // 获取屏幕高度
        screenH = dis.getHeight() - 50;//如果没有屏蔽标题栏，记得要减去标题栏的高度

        dragTV = (DragTV)findViewById(R.id.DragTV_textView);
        dragTV.setScreenWidth(screenW);
        dragTV.setScreenheight(screenH);
    }

}
