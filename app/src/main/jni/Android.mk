LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := test
LOCAL_SRC_FILES := test.cpp
LOCAL_LDFLAGS += -L$(LOCAL_PATH) -lnative

include $(BUILD_SHARED_LIBRARY)