package net.jackytallow.thinkvideo.utils

import android.util.Log

/**
 * @author jacky
 * @date 2020/6/16
 * @version 1.0.0 Log日志封装类
 */
class LogUtils {
    //开关
    val DEBUG = true

    //TAG
    val TAG = "LogConfig"

    //五种等级的DEBUG
    fun d(text: String?) {
        if (DEBUG) {
            Log.d(TAG, text)
        }
    }

    fun i(text: String?) {
        if (DEBUG) {
            Log.i(TAG, text)
        }
    }

    fun w(text: String?) {
        if (DEBUG) {
            Log.w(TAG, text)
        }
    }

    fun e(text: String?) {
        if (DEBUG) {
            Log.e(TAG, text)
        }
    }
}