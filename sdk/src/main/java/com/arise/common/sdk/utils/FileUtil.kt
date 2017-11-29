package com.arise.common.sdk.utils

import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * Created by wudaming on 2017/11/16.
 */


object FileUtil {
    val BASE_PATH = Environment.getExternalStorageDirectory().path + "/arise"
    val CACHE_PATH = BASE_PATH + "/cache"
    val NET_CACHE_PATH = CACHE_PATH + "/network"

    fun writeFileFromStream(inStream: InputStream, path: String): File {
        val file = File(path)
        if (!file.exists() && file.mkdirs()) {
            throw Exception("创建文件失败path:" + path)
        }
        val outStream = FileOutputStream(file)
        val buff = ByteArray(1024 * 5)
        var length = 0
        while (length != -1) {
            length = inStream.read(buff, 0, buff.size)
            outStream.write(buff)
        }
        outStream.flush()
        return file

    }
}