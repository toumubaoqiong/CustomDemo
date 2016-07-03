package com.vince.demo.customdemo.crash_handler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.PushbackInputStream;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Properties;

/**
 * decription ：
 * author ： vince
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = CrashHandler.class.getSimpleName();
    //sd dir
    private static final String SDCARD_PATH = Environment.getExternalStorageDirectory().toString();
    private static final String DEFAULT_CRASH_LOG_PATH = SDCARD_PATH + File.separator + ".crash" + File.separator;
    //1M  log cache max size
    private static final long DEFAULT_CRASH_CACHE_SIZE = 1048576L;
    // restart times
    private static final int RESTART_COUNT = 3;
    //log file extension
    private static final String CRASH_REPORTER_EXTENSION = ".cr";
    private static CrashHandler mInstance;
    private Context mContext = null;
    //log file path
    private String mLogPath;
    //restart count file path
    private String mCountFilePath;
    //log cache size
    private long mCrashCacheSize;
    private boolean needUIReport;
    private boolean needToast;
    private Properties mDeviceCrashInfo;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private CrashHandler() {
        this.mLogPath = DEFAULT_CRASH_LOG_PATH;
        this.mCountFilePath = null;
        this.needUIReport = true;
        this.needToast = true;
        this.mDeviceCrashInfo = new Properties();
    }

    /**
     * @return
     * @des: get single instance
     */
    public static CrashHandler getInstance() {
        if (mInstance == null) {
            mInstance = new CrashHandler();
        }

        return mInstance;
    }

    public void init(Context context, boolean needToast) {
        String defLogPath = DEFAULT_CRASH_LOG_PATH + context.getPackageName() + File.separator;
        this.init(context, defLogPath, 1048576L, needToast);
    }

    public void init(Context context, String logPath, long crashCacheSize, boolean needToast) {
        this.mContext = context;
        this.mLogPath = logPath;
        this.mCrashCacheSize = crashCacheSize;
        this.needToast = needToast;
        this.mCountFilePath = context.getCacheDir().toString() + File.separator + "CrashLaunchCount.sav";
    }

    /**
     * description:register crash handler
     */
    public void registerCrashHandler() {
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        if (!this.handleException(throwable) && this.mDefaultHandler != null) {
            this.mDefaultHandler.uncaughtException(thread, throwable);
        } else {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException var4) {
                Log.e(TAG, "error : ", var4);
            }

            Process.killProcess(Process.myPid());
            System.exit(1);
        }
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        } else {
            (new Thread() {
                public void run() {
                    try {
                        Looper.prepare();
                        if (CrashHandler.this.mContext != null && CrashHandler.this.needToast) {
                            Toast.makeText(CrashHandler.this.mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_SHORT).show();
                        }

                        Looper.loop();
                    } catch (Exception var2) {
                    }

                }
            }).start();

            String log = this.saveCrashInfoToFile(ex);
            this.recordReStartCount();
            if (this.needUIReport) {
                this.autoRestart();
            }

            return true;
        }
    }

    /**
     * description:save crash log file
     */
    private String saveCrashInfoToFile(Throwable ex) {
        StringWriter info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        ex.printStackTrace(printWriter);

        for (Throwable cause = ex.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }

        String result = info.toString();
        printWriter.close();
        this.mDeviceCrashInfo.put("STACK_TRACE", result);

        try {
            try {
                if (mLogPath == null) {
                    Log.i(TAG, "log path invalid, can\'t save crash log file !!");
                    return result;
                }

                String fileName = this.resolveLogFileName();
                if (fileName == null) {
                    Log.i(TAG, "log file name invalid, can\'t save crash log file !!");
                    return result;
                }

                String filePath = mLogPath + fileName;
                if (!FileUtils.checkFileDirExisted(filePath)) {
                    Log.i(TAG, "save crash info: create crash file dir error !");
                    return result;
                }

                this.checkCacheSize();
                Log.i("filePath" , filePath);
                FileOutputStream trace = new FileOutputStream(filePath);
                this.mDeviceCrashInfo.store(trace, "");
                trace.flush();
                trace.close();
                Log.i(TAG, "save a crash file: " + mLogPath + fileName);
            } catch (Exception var14) {
                var14.printStackTrace();
                Log.i(TAG, "an error occured while writing report file...", var14);
            }

            return result;
        } finally {
        }
    }

    /**
     * get file name
     */
    private String resolveLogFileName() {
        try {
            Calendar e = Calendar.getInstance();
            return String.format("crash--%d-%d-%d-%d.%d.%d%s", new Object[]{Integer.valueOf(e.get(1)), Integer.valueOf(e.get(2) + 1), Integer.valueOf(e.get(5)), Integer.valueOf(e.get(11)), Integer.valueOf(e.get(12)), Integer.valueOf(e.get(13)), ".cr"});
        } catch (Exception var2) {
            return null;
        }
    }

    /**
     * check Cache Size
     */
    private void checkCacheSize() {
        String cachePath = mLogPath;
        if (cachePath != null) {
            long cacheSize = FileUtils.getFileSize(cachePath);
            if (cacheSize >= this.mCrashCacheSize) {
                boolean ret = FileUtils.deleteFolder(cachePath);
                Log.i(TAG, "the cache size is rearch max size, we will clear it, clear: " + ret);
            }
        }
    }

    /**
     * description: record restart count
     */
    private void recordReStartCount() {
        int count = this.readReStartCount();
        Log.d(TAG, "read re-start count: " + count);
        ++count;
        this.saveReStartCount(count);
    }

    /**
     * description: read restart count from file
     */
    private int readReStartCount() {
        if (this.mCountFilePath == null) {
            return 0;
        } else {
            File file = new File(this.mCountFilePath);
            if (!file.exists()) {
                return 0;
            } else {
                int count = 0;

                try {
                    FileInputStream e = new FileInputStream(file);
                    ObjectInputStream oin = new ObjectInputStream(e);
                    count = oin.readInt();
                    oin.close();
                    e.close();
                } catch (Exception var5) {
                    Log.e(TAG, "an error occured while writing count file ...", var5);
                }

                return count;
            }
        }
    }

    /**
     * description: save restart count
     */
    private void saveReStartCount(int count) {
        if (this.mCountFilePath != null) {
            try {
                if (!FileUtils.checkFileDirExisted(this.mCountFilePath)) {
                    Log.e(TAG, "save count file: create count file dir error !");
                    return;
                }

                FileOutputStream e = new FileOutputStream(this.mCountFilePath);
                ObjectOutputStream oout = new ObjectOutputStream(e);
                oout.writeInt(count);
                oout.writeLong(System.currentTimeMillis());
                oout.flush();
                e.flush();
                oout.close();
                e.close();
            } catch (Exception var4) {
                Log.e(TAG, "an error occured while writing count file ...", var4);
            }
        }
    }

    /**
     * description: restart app
     */
    private void autoRestart() {
        Intent intent = new Intent(this.mContext, CrashReportActivity.class);
        intent.setFlags(337707008);
        PendingIntent contentIntent = PendingIntent.getActivity(this.mContext, 0, intent, 134217728);
        AlarmManager mgr = (AlarmManager) this.mContext.getSystemService("alarm");
        if (this.isReStartTooMany()) {
            mgr.cancel(contentIntent);
        } else {
            mgr.set(1, System.currentTimeMillis() + 300L, contentIntent);
            Log.d(TAG, "we restart the app");
        }
    }

    public boolean isReStartTooMany() {
        int count = this.readReStartCount();
        return count >= 3;
    }

    /**
     * read restart time
     */
    public long readRestartTime() {
        if(this.mCountFilePath == null) {
            return 0L;
        } else {
            File file = new File(this.mCountFilePath);
            if(!file.exists()) {
                return 0L;
            } else {
                long time = 0L;

                try {
                    FileInputStream e = new FileInputStream(file);
                    ObjectInputStream oin = new ObjectInputStream(e);
                    oin.readInt();
                    time = oin.readLong();
                    oin.close();
                    e.close();
                } catch (Exception var6) {
                    Log.e(TAG, "an error occured while writing count file ...", var6);
                }

                return time;
            }
        }
    }

    /**
     * clean restart count
     */
    public void cleanReStartCount() {
        Log.d(TAG, "clean re-start count !!");
        this.saveReStartCount(0);
    }

    public void setCrashNeedUIReport(boolean flag) {
        this.needUIReport = flag;
    }
}
