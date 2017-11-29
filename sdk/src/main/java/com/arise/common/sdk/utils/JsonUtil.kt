package com.arise.common.sdk.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

/**
 * Created by wudaming on 2017/11/20.
 */
object JsonUtil {

    val mapper = jacksonObjectMapper()

    inline fun<reified T: Any> readValue(jsonString: String): Any? {
        var result: Any? = null
        try {
            result =mapper.readValue<T>(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    fun writeValueAsString(any: Any): String = mapper.writeValueAsString(any)
}