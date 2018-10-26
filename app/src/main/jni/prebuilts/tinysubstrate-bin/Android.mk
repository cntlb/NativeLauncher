LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := tinysubstrate-bin
LOCAL_SRC_FILES := libmcpelauncher_tinysubstrate.so
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)
include $(PREBUILT_SHARED_LIBRARY)

