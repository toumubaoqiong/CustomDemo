package com.vince.demo.customdemo.crash_handler;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * decription ：file utils
 */
public class FileUtils {

    /**
     * get parent dir
     */
    public static final String getParentDir(String path) {
        if(path == null) {
            return null;
        } else {
            try {
                int e = path.lastIndexOf("/");
                return e <= -1?null:path.substring(0, e);
            } catch (Exception var2) {
                var2.printStackTrace();
                return null;
            }
        }
    }

    /**
     *description:check file dir is existed
     */
    public static final boolean checkFileDirExisted(String fileName) {
        String dir = getParentDir(fileName);
        if(dir == null) {
            return false;
        } else {
            File fDir = new File(dir);

            try {
                if(!fDir.exists() && !fDir.mkdirs()) {
                    Log.d("StoreUtils", "create folder " + dir + " failed");
                }

                return true;
            } catch (SecurityException var4) {
                var4.printStackTrace();
                return false;
            }
        }
    }

    /**
     *description:get file or file folder size
     */
    public static final long getFileSize(String path) {
        if(path == null) {
            return 0L;
        } else {
            File file = new File(path);
            File[] files = null;
            if(file.isDirectory()) {
                files = file.listFiles();
                if(files == null) {
                    return 0L;
                } else {
                    long size = 0L;

                    for(int i = 0; i < files.length; ++i) {
                        if(files[i].isDirectory()) {
                            size += getFileSize(files[i].getAbsolutePath());
                        } else {
                            size += files[i].length();
                        }
                    }

                    return size;
                }
            } else {
                return file.length();
            }
        }
    }

    /**
     * delete directory or file
     */
    public static final boolean deleteFolder(String path) {
        if(path == null) {
            return false;
        } else {
            File file = new File(path);
            return !file.exists()?false:(file.isFile()?deleteFile(path):deleteDirectory(path));
        }
    }

    /**
     * delete file
     */
    public static final boolean deleteFile(String path) {
        if(path == null) {
            return false;
        } else {
            File file = new File(path);
            if(file.exists() && file.isFile()) {
                boolean ret = true;

                try {
                    ret = file.delete();
                } catch (Exception var4) {
                    var4.printStackTrace();
                    ret = false;
                }

                return ret;
            } else {
                return false;
            }
        }
    }

    /**
     * delete directory
     */
    public static final boolean deleteDirectory(String path) {
        if(path == null) {
            return false;
        } else {
            File dirFile = new File(path);
            if(dirFile.exists() && dirFile.isDirectory()) {
                boolean ret = true;
                File[] files = dirFile.listFiles();

                for(int e = 0; e < files.length; ++e) {
                    if(files[e].isDirectory()) {
                        ret &= deleteDirectory(files[e].getAbsolutePath());
                    } else {
                        ret &= deleteFile(files[e].getAbsolutePath());
                    }
                }

                try {
                    ret &= dirFile.delete();
                } catch (Exception var5) {
                    var5.printStackTrace();
                    ret = false;
                }

                return ret;
            } else {
                return false;
            }
        }
    }

    public static boolean compare2Sec(long time1, long time2, int dur) {
        Log.v("StoreUtils", String.valueOf(Math.abs(time1 - time2) / 1000L));
        return Math.abs(time1 - time2) / 1000L > (long)dur;
    }

    /**
     * 收集设备参数信息
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx, Map<String,String> infos) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            //Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                //Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }
}
