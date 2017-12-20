package com.arise.common.ming.config

import android.content.Context
import com.facebook.cache.common.CacheErrorLogger
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.common.internal.Supplier
import com.facebook.common.util.ByteConstants
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.cache.MemoryCacheParams
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.orhanobut.logger.Logger
import java.io.File
import java.lang.StringBuilder

/**
 * Created by wudaming on 2017/12/20.
 */
class FrescoConfig {

    private val diskCacheSize = 100L * ByteConstants.MB

    fun init(context: Context) {


        //磁盘缓存
        val mainDiskCacheConfig = DiskCacheConfig.newBuilder(context)
                .setBaseDirectoryName("image")
                .setBaseDirectoryPath(File(Config.CACHE_PATH))
//                和setBaseDirectoryPath一个作用
//                .setBaseDirectoryPathSupplier{File(Config.CACHE_PATH)}
                .setMaxCacheSize(diskCacheSize)
                .setMaxCacheSizeOnLowDiskSpace(diskCacheSize / 4)
                .setMaxCacheSizeOnVeryLowDiskSpace(diskCacheSize / 10)
                .setCacheErrorLogger(MyCacheErrorLogger())
//                暂时不设置这个
//                .setDiskTrimmableRegistry()
                .setVersion(1)
                .build()


        //内存缓存
        val bitmapCacheParams = MemoryCacheParams(50 * ByteConstants.MB,
                1000,
                10 * ByteConstants.MB,
                100,
                1 * ByteConstants.MB
        )

        val config = ImagePipelineConfig.newBuilder(context)
                .setBitmapMemoryCacheParamsSupplier { bitmapCacheParams }
//                目前不用设置，默认是使用的图片地址来生成缓存key，如果地址不断变动的话就需要自定义一个了
//                参考http://blog.csdn.net/boycmy/article/details/51338671
//                .setCacheKeyFactory(cacheKeyFactory)
                .setDownsampleEnabled(true)
//                .setWebpSupportEnabled(true)
//                没太明白，编码后的图片缓存？用默认的
//                .setEncodedMemoryCacheParamsSupplier(encodedCacheParamsSupplier)
//                用默认的就好，配置各种工作的线程池
//                .setExecutorSupplier(executorSupplier)
//                .setImageCacheStatsTracker(imageCacheStatsTracker)
                .setMainDiskCacheConfig(mainDiskCacheConfig)
//                .setMemoryTrimmableRegistry(memoryTrimmableRegistry)
//                .setPoolFactory(poolFactory)
//                .setProgressiveJpegConfig(progressiveJpegConfig)
//                .setRequestListeners(requestListeners)
//                .setSmallImageDiskCacheConfig(smallImageDiskCacheConfig)
                .build()
        Fresco.initialize(context, config)
    }
}

class MyCacheErrorLogger : CacheErrorLogger {
    override fun logError(category: CacheErrorLogger.CacheErrorCategory?, clazz: Class<*>?, message: String?, throwable: Throwable?) {
        val finalMessage = StringBuilder()
        category?.let { finalMessage.append("错误类型：" + it.name + "\n") }
        clazz?.let { finalMessage.append("错误发生class：" + it.simpleName + "\n") }
        message?.let { finalMessage.append("message:" + it + "\n") }
        throwable?.let { finalMessage.append("stacktrace:" + it.message + "\n") }
        Logger.t("fresco").e(finalMessage.toString())
    }

}