#include "com_vince_demo_customdemo_jni_JniDemo.h"
#include <android/log.h>
#define LOG_TAG "Vince"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

JNIEXPORT jstring JNICALL Java_com_vince_demo_customdemo_jni_JniDemo_getStringFromNative
  (JNIEnv *env, jclass jclas){
        return env->NewStringUTF("hello Jni");
  }