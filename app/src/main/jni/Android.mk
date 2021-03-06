LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := test
LOCAL_SRC_FILES := $(wildcard $(LOCAL_PATH)/src/*)
LOCAL_LDFLAGS += -L$(LOCAL_PATH) -lnative
LOCAL_SHARED_LIBRARIES := tinysubstrate-bin
LOCAL_LDLIBS := -llog
LOCAL_CPPFLAGS := -std=c++11
LOCAL_C_INCLUDES +=  $(LOCAL_PATH)/include

include $(BUILD_SHARED_LIBRARY)

$(call import-add-path, $(LOCAL_PATH)/prebuilts)
$(call import-module, tinysubstrate-bin)