package com.arise.common.sdk.db

import android.content.SharedPreferences
import com.arise.common.sdk.utils.JsonUtil

/**
 * 相关文档--http://blog.csdn.net/yanbober/article/details/47866369
 * 实际上是一种xml文件存储方式。
 * commit是同步写入文件并获得结果！
 * apply是异步写入文件，不关心结果，速度块！
 * 两种方式对内存的操作没有区别，所以在写入数据没有产生错误的情况下不影响操作结果。
 */
class PreferenceManagerHelp(private val sharedPreferences: SharedPreferences) {

    fun putString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String, default: String = ""): String {
        return sharedPreferences.getString(key, default)
    }

    fun putBoolean(key:String,value:Boolean){
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String,default: Boolean = false):Boolean{
        return sharedPreferences.getBoolean(key, default)
    }

    /**
     * 仅支持POJO对象
     */
    fun <T>getObject(key: String,clazz: Class<T>):T?{
        val value = getString(key)
        return JsonUtil.readValue(value,clazz)
    }
    /**
     * 仅支持POJO对象
     * @param value 不空时存储，空时删除该item
     */
    fun putObject(key: String,value:Any?){
        if (value != null){
            val realValue = JsonUtil.writeValueAsString(value)
            putString(key,realValue)
        }else{
            val edit = sharedPreferences.edit()
            edit.remove(key)
            edit.apply()
        }

    }
}