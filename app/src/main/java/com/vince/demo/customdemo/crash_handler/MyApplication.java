package com.vince.demo.customdemo.crash_handler;

import android.app.Application;
import android.util.Log;

/**
 * decription ：custom Application
 */
public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();

    private  String mContent;

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public MyApplication() {
    }

    public void onCreate() {
        super.onCreate();
//        initApplication(this);
    }

    public static void initApplication(Application app) {
        if (app != null) {
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(app, true);
            CrashHandler.getInstance().setCrashNeedUIReport(true);
            if (crashHandler.isReStartTooMany()) {
                Log.d(TAG, "re-start is too many times we abandon crash handle !");
                if (FileUtils.compare2Sec(crashHandler.readRestartTime(), System.currentTimeMillis(), 10)) {
                    crashHandler.registerCrashHandler();
                }

                crashHandler.cleanReStartCount();
            } else {
                crashHandler.registerCrashHandler();
            }
        }
    }
}
