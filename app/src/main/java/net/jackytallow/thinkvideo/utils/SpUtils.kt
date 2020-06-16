package net.jackytallow.thinkvideo.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * @author jacky
 * @date 2020/6/16
 * @version 1.0.0]
 * SharedPreferences封装工具类
 */
class SpUtils {
    val NAME: String = "config"

    //存储String类型参数 <键，值>
    fun putString(mContext: Context, key: String, value: String) {
        val sp: SharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        sp.edit().putString(key, value).apply()
    }

    //读取String类型参数<键，默认值>
    fun getString(mContext: Context, key: String, defvalue: String): String {
        val sp: SharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        return sp.getString(key, defvalue)!!
    }

    //存储Int类型参数
    // 键 值
    fun putInt(mContext: Context, key: String, value: Int) {
        val sp: SharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        sp.edit().putInt(key, value).apply()
    }

    //读取Int类型参数
    // 键 默认值
    fun getInt(mContext: Context, key: String, defvalue: Int): Int {
        val sp: SharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        return sp.getInt(key, defvalue)!!
    }

    //存储Boolean类型参数 <键，值>
    fun putBoolean(mContext: Context, key: String, value: Boolean) {
        val sp: SharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        sp.edit().putBoolean(key, value).apply()
    }

    //读取Boolean类型参数 : 键 默认值
    fun getBoolean(mContext: Context, key: String, defvalue: Boolean): Boolean {
        val sp: SharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        return sp.getBoolean(key, defvalue)!!
    }

    //删除  单个
    fun deleteShare(mContext: Context, key: String) {
        val sp: SharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        sp.edit().remove(key).apply()
    }

    //删除  全部
    fun deleteAll(mContext: Context) {
        val sp: SharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        sp.edit().clear().apply()
    }

}