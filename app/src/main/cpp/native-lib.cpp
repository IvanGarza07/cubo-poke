#include <jni.h>
#include <string>
#include "dev-env.cpp"

extern "C" JNIEXPORT jstring

JNICALL
Java_com_cubo_app_presentation_utils_AppKeys_domain(JNIEnv *env, jobject object) {
    return env->NewStringUTF(domain.c_str());
}