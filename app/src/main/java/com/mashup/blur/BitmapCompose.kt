package com.mashup.blur

import android.graphics.Bitmap
import kotlin.math.max

fun composeBitmap(baseBitmap: Bitmap, composeBitmap: Bitmap): Bitmap {
    val newBitmap = baseBitmap.copy(baseBitmap.config, true)
//    val newBitmap = Bitmap.createBitmap(
//        max(baseBitmap.width, composeBitmap.width),
//        max(baseBitmap.height, composeBitmap.height),
//        baseBitmap.config
//    )
    for (i in 0 until composeBitmap.width) {
        for (j in 0 until composeBitmap.height) {
            newBitmap.setPixel(
                i,
                j,
                baseBitmap.getPixel(i, j) compose composeBitmap.getPixel(i, j)
            )
        }
    }
    return newBitmap
}
