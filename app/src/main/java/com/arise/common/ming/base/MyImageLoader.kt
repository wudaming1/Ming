package com.arise.common.ming.base

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.facebook.common.references.CloseableReference
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.image.CloseableBitmap
import com.facebook.imagepipeline.request.ImageRequest
import com.meili.component.imagepicker.imageloadframe.ImageLoadFrame
import com.orhanobut.logger.Logger
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.drawee.backends.pipeline.PipelineDraweeController
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import java.io.File


/**
 * fresco为图片加载引擎
 */
class MyImageLoader: ImageLoadFrame {

    override fun isFresco(): Boolean {
        return true
    }

    override fun displayImage(context: Context, imagePath: String, view: View, width: Int, height: Int) {
        val request = ImageRequestBuilder
                .newBuilderWithSource(Uri.fromFile(File(imagePath)))
                .setResizeOptions(ResizeOptions(width, height))
                .build()
        val controller = Fresco.newDraweeControllerBuilder().setOldController((view as SimpleDraweeView).controller).setImageRequest(request)
                .setAutoPlayAnimations(true)
                .build() as PipelineDraweeController
        (view as SimpleDraweeView).controller = controller
    }

    override fun clearMemoryCache(context: Context) {
        Fresco.getImagePipeline().clearMemoryCaches()
    }


}