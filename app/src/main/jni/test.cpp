//
// Created by jmu on 18-10-23.
//

#include <jni.h>
#include <Util.h>
#include <substrate.h>
#include <dlfcn.h>
#include <unistd.h>
#include "mclog.h"
#include "com_example_launcher_MainActivity.h"

#define LOG_TAG "test.cpp"

static void *handle;

MSHook(int, Util_add, int a, int b){
    LOGE("hook Util::add")
    return _Util_add(a, b);
}

JNICALL jstring Java_com_example_launcher_MainActivity_getStringUTF(JNIEnv *env, jobject) {

    return env->NewStringUTF("Hello Native!");
}

JNICALL jint Java_com_example_launcher_MainActivity_add(JNIEnv *env, jobject, jint a, jint b) {
    return Util::add(a, b);
}

JNICALL void Java_com_example_launcher_MainActivity_initHooks(JNIEnv *env, jobject, jstring pathname) {
    const char *filename = env->GetStringUTFChars(pathname, nullptr);
    LOGE("so path:%s", filename)
    handle = dlopen(filename, RTLD_LAZY);
    if (handle == nullptr) {
        LOGE("load library[%s] error:%s", filename, dlerror())
        return;
    }
    MSHookFunction((void *) &Util::add, (void *) &$Util_add, (void **) &_Util_add);
}

JNIEXPORT void JNICALL Java_com_example_launcher_MainActivity_release
        (JNIEnv *, jobject){
    LOGE("MainActivity_release")
    if (handle && dlclose(handle)) {
        LOGE("close library error:%s", dlerror())
    }
    handle = 0;
}

JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved){
    LOGE("JNI_OnLoad")
    return JNI_VERSION_1_6;
}

JNIEXPORT void JNI_OnUnload(JavaVM *vm, void *reserved) {
    LOGE("JNI_OnUnload")
    if (handle && dlclose(handle)) {
        LOGE("close library error:%s", dlerror())
    }
    handle = 0;
}
