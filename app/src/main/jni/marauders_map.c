//
// Created by jmu on 18-10-25.
//

#include <stdint.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <jni.h>
#include <android/log.h>
#include <malloc.h>
#include <substrate.h>

#define LOG_TAG "BlockLauncher/Marauder"


static uintptr_t remapped_text_offset = 0;
static uintptr_t original_text_offset = 0;
static size_t remapped_text_length = 0;
static int remapped_text_fd = -1;

int marauder_remap_text(uintptr_t originalBegin, size_t length, const char *sharedPath) {
    /* I solumnly swear that I am up to no good.
     * Allocates a shared file to hold the text section of libminecraftpe.so
     * by creating a new file at sharedPath, truncating it to length,
     * mapping this as a shared mapping in read/write mode, copying in the original,
     * and finally unmapping the original, replacing it with our file mapped in read/execute mode
     */
    remapped_text_fd = open(sharedPath, O_RDWR | O_CREAT, S_IRWXU);

    if (remapped_text_fd < 0) {
        __android_log_print(ANDROID_LOG_INFO, LOG_TAG, "failed to create file: %i\n", errno);
        return 0;
    }
    unlink(sharedPath);
    ftruncate(remapped_text_fd, length);
    void *newMapping = mmap(NULL, length, PROT_READ | PROT_WRITE, MAP_SHARED, remapped_text_fd, 0);
    if (newMapping == MAP_FAILED) {
        __android_log_print(ANDROID_LOG_INFO, LOG_TAG, "failed to map file: %i\n", errno);
        return 0;
    }
    void *oldMapping = (void *) originalBegin;
    memcpy(newMapping, oldMapping, length);
    munmap(oldMapping, length);
    void *reMapping = mmap(oldMapping, length, PROT_READ | PROT_EXEC, MAP_SHARED | MAP_FIXED, remapped_text_fd, 0);
    if (reMapping == MAP_FAILED) return 0;
    remapped_text_offset = (uintptr_t) newMapping;
    remapped_text_length = length;
    original_text_offset = originalBegin;
    return 1;
}

static void *marauder_translation_function(void *input) {
    uintptr_t addr = (uintptr_t) input;
    //__android_log_print(ANDROID_LOG_INFO, LOG_TAG, "Input %x", addr);
    if (addr >= original_text_offset && addr < original_text_offset + remapped_text_length) {
        addr = (addr - original_text_offset) + remapped_text_offset;
        //__android_log_print(ANDROID_LOG_INFO, LOG_TAG, "Remapping to %x", addr);
    }
    return (void *) addr;
}

JNIEXPORT jlong JNICALL Java_net_zhuoweizhang_mcpelauncher_MaraudersMap_remapText
        (JNIEnv *env, jclass clazz, jlong addr, jlong len, jstring tempFilePath) {
    const char *pathChars = (*env)->GetStringUTFChars(env, tempFilePath, NULL);
    int status = marauder_remap_text((uintptr_t) addr, len, pathChars);
    (*env)->ReleaseStringUTFChars(env, tempFilePath, pathChars);
    return status == 0 ? -1 : remapped_text_offset;
}

JNIEXPORT void JNICALL Java_net_zhuoweizhang_mcpelauncher_MaraudersMap_setTranslationFunction
        (JNIEnv *env, jclass clazz, jstring tempFilePath) {
    const char *pathChars = (*env)->GetStringUTFChars(env, tempFilePath, NULL);
    char *newChar = malloc(strlen(pathChars) + 1);
    strcpy(newChar, pathChars);
    MSSetAddressTranslationFunction(marauder_translation_function, newChar);
    (*env)->ReleaseStringUTFChars(env, tempFilePath, pathChars);
//__android_log_print(ANDROID_LOG_INFO, LOG_TAG, "Set translation function");
}

JNIEXPORT jint JNICALL Java_net_zhuoweizhang_pokerface_PokerFace_mprotect
        (JNIEnv *env, jclass clazz, jlong addr, jlong len, jint prot) {
    return mprotect((void *)(uintptr_t) addr, len, prot);
}

JNIEXPORT jlong JNICALL Java_net_zhuoweizhang_pokerface_PokerFace_sysconf
        (JNIEnv *env, jclass clazz, jint name) {
    long result = sysconf(name);
    return result;
}
