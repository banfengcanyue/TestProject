//
// Created by Administrator on 2020/9/2.
//
#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_bfcy_testproject_TestDemo_getString(JNIEnv *env, jobject thiz) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_bfcy_testproject_TestDemo_getName(JNIEnv *env, jobject thiz) {
    // TODO: implement getName()
    std::string hello = "Xiao Hong";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_bfcy_testlibrary_TestDemo_getString(JNIEnv *env, jobject thiz) {
    // TODO: implement getString()
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_bfcy_testlibrary_TestDemo_getName(JNIEnv *env, jobject thiz) {
    // TODO: implement getName()
    std::string hello = "Xiao Hong";
    return env->NewStringUTF(hello.c_str());
}