package com.mashup.blur

import android.graphics.Bitmap

fun getMotionBlurBitmap(bitmap: Bitmap, blurAmount: Int, interval: Int, direction: Direction): Bitmap {
    var composeBitmap = bitmap.copy(bitmap.config, true)
    for (i in 0 until blurAmount) {
        val shiftBitmap = when (direction) {
            Direction.LEFT -> bitmapLeftShift(bitmap, interval * i)
            Direction.TOP -> bitmapTopShift(bitmap, interval * i)
            else -> TODO("Not Implemented")
        }
        composeBitmap = composeBitmap(composeBitmap, shiftBitmap)
    }
    return composeBitmap
}

enum class Direction {
    LEFT, TOP, RIGHT, BOTTOM
}
