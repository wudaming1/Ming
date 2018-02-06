package com.arise.common.sdk.utils

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

/**
 * Created by wudaming on 2017/11/20.
 */
object JsonUtil {

    private val mapper = jacksonObjectMapper()

    fun <T> readValue(jsonString: String, clazz: Class<T>): T? {
        if (jsonString.isEmpty())
            return null
        var result: T? = null
        try {

            result = mapper.readValue<T>(jsonString, clazz)
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: MismatchedInputException) {
            e.printStackTrace()
        }
        return result
    }

    fun writeValueAsString(any: Any): String = mapper.writeValueAsString(any)
}