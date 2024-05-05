package com.mashup

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.ui.graphics.PixelMap
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat

fun merge(context: Context): Bitmap {
    val mat1 = Utils.loadResource(context, R.drawable.dummy1)
    val mat2 = Utils.loadResource(context, R.drawable.dummy2)
    val resultMat = Mat()

    Log.e("Mat Size:::", "${mat1.width()} ${mat1.height()} ${mat1.size()}")
    val bitmapConfig = Bitmap.Config.ARGB_8888
    val bitmap = Bitmap.createBitmap(mat1.width(), mat2.height(), bitmapConfig)

    Core.addWeighted(mat1, 0.5, mat2, 0.5, 0.0, resultMat)

    Utils.matToBitmap(resultMat, bitmap)
    return bitmap
}

fun merge(pixelMap: PixelMap) {
    val cutData = pixelMap.buffer.copyOfRange(pixelMap.width / 2, pixelMap.width)
    val mat = Mat()
    mat.put(0, 0, cutData)
}
