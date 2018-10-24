//
// Created by jmu on 18-10-23.
//

#include <jni.h>
#include <Util.h>

extern "C" {
JNICALL jstring Java_com_example_launcher_MainActivity_getStringUTF(JNIEnv *env, jobject) {

    return env->NewStringUTF("Hello Native!");
}
JNICALL jint Java_com_example_launcher_MainActivity_add(JNIEnv *env, jobject, jint a, jint b) {

    return Util::add(a, b);
}

}