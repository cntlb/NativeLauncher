//
// Created by user on 2016/6/24.
//

#ifndef IMPORT_PESO_MYLOG_H
#define IMPORT_PESO_MYLOG_H

#include <android/log.h>

#define MCDEBUG
//发布时为static, 系统命名函数/变量名, so不易读懂
#ifdef MCDEBUG
    #define _MC_STATIC
#else
    #define _MC_STATIC static
#endif

#ifdef MCDEBUG
    #define  ANDROID_LOG(prio, format, args...) __android_log_print(prio, LOG_TAG, "[%4d] " format, __LINE__, ##args );
    #define  LOGE(format, args...)  ANDROID_LOG(ANDROID_LOG_ERROR, format, ##args)
    #define  LOGI(format, args...)  ANDROID_LOG(ANDROID_LOG_INFO,  format, ##args)
    #define  LOGD(format, args...)  ANDROID_LOG(ANDROID_LOG_DEBUG, format, ##args)
    #define  LOGW(format, args...)  ANDROID_LOG(ANDROID_LOG_WARN,  format, ##args)
    #define  LOGERET(arg)  ANDROID_LOG(ANDROID_LOG_ERROR,  "%s need return type: %s",__FUNCTION__, #arg)
    #define  LOGEP(ptr)  LOGE(#ptr" at:%p", ptr)
#else
    #define  LOGE(format, args...)
    #define  LOGI(format, args...)
    #define  LOGD(format, args...)
    #define  LOGW(format, args...)
    #define  LOGEP(ptr)
#endif

#endif //IMPORT_PESO_MYLOG_H
