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
    #define  PRINT_FIELDS(ptr, size) \
            LOGE("以int为单位打印"#ptr"字段=========")\
            for(int mclog_i=0; mclog_i<size;mclog_i++){ \
                LOGE(#ptr" int[%d] = %d",mclog_i,*(int*)((char*)ptr+mclog_i*4)) \
            }

    #define  PRINT_FIELDS_CHAR(ptr, size) \
            LOGE("以char为单位打印"#ptr"字段=========")\
            for(int mclog_i=0; mclog_i<size;mclog_i++){ \
                LOGE(#ptr" char[%d] = %d",mclog_i,*(char*)((char*)ptr+mclog_i)) \
            }

    #define  PRINT_FIELDS_SHORT(ptr, size) \
            LOGE("以short为单位打印"#ptr"字段=========")\
            for(int mclog_i=0; mclog_i<size;mclog_i++){ \
                LOGE(#ptr" short[%d] = %d",mclog_i,*(short*)((char*)ptr+mclog_i*2)) \
            }
    #define  PRINT_FIELDS_ALL(ptr, size)  PRINT_FIELDS(ptr, size) \
                                          PRINT_FIELDS_CHAR(ptr, 4*size) \
                                          PRINT_FIELDS_SHORT(ptr, 2*size)
#else
    #define  LOGE(format, args...)
    #define  LOGI(format, args...)
    #define  LOGD(format, args...)
    #define  LOGW(format, args...)
    #define  LOGEP(ptr)
    #define  PRINT_FIELDS(ptr, size)
    #define  PRINT_FIELDS_CHAR(ptr, size)
    #define  PRINT_FIELDS_SHORT(ptr, size)
#endif

#ifdef MCDEBUG
    #include <stddef.h>

    //implementation in util.cpp
    void printChar_fcn(const void *start, size_t len, bool breakZero = false);
    char* printBinary_fcn(void *start, size_t len);

    #define getByteString(start, len) printBinary_fcn(start, len)
    #define getCharString(start, len) printChar_fcn(start, len)
#else
    #define printBinary(start, len)
    #define printChar(start, len)
#endif

#endif //IMPORT_PESO_MYLOG_H
