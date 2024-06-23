#include <jni.h>
#include <math.h>
#include <iostream>

jobject squareRootCallback = nullptr;

extern "C"
JNIEXPORT jdouble JNICALL
Java_demo_mohitmalhotra_jnidemo_MainActivity_getSquareRoot(JNIEnv *env, jobject thiz, jint value) {
    jdouble result = sqrt(value);
    std::cout << "SQRT (" << value << ") = " << result << std::endl;
    return result;
}
extern "C"
JNIEXPORT void JNICALL
Java_demo_mohitmalhotra_jnidemo_MainActivity_getSquareRootAsync(JNIEnv *env, jobject thiz,
                                                                jint value) {
    jdouble result = Java_demo_mohitmalhotra_jnidemo_MainActivity_getSquareRoot(env, thiz, value);
    if(squareRootCallback) {
        // fetch method from method id
        jclass clazz = env->GetObjectClass(squareRootCallback);
        jmethodID mid = env->GetMethodID(clazz, "getResult", "(D)V");
        env->CallVoidMethod(squareRootCallback, mid, result);
    }

}
extern "C"
JNIEXPORT void JNICALL
Java_demo_mohitmalhotra_jnidemo_MainActivity_setSquareRootCallback(JNIEnv *env, jobject thiz,
                                                                   jobject callback) {
    squareRootCallback = env->NewGlobalRef(callback); // take global reference of callback
}