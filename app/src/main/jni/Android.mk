LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

# for logging
LOCAL_LDLIBS    += -llog
LOCAL_MODULE    := jnidemo
LOCAL_SRC_FILES := com_vince_demo_customdemo_jni_JniDemo.cpp

include $(BUILD_SHARED_LIBRARY)