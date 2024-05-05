package com.mashup.blur

import android.graphics.Bitmap

fun bitmapLeftShift(bitmap: Bitmap, x: Int): Bitmap = bitmapShift(bitmap, x, 0)

fun bitmapTopShift(bitmap: Bitmap, y: Int): Bitmap = bitmapShift(bitmap, 0, y)

fun bitmapShift(bitmap: Bitmap, x: Int, y: Int): Bitmap {
    val newBitmap = Bitmap.createBitmap(bitmap.width - x, bitmap.height - y, bitmap.config)
    for (i in 0 until newBitmap.width) {
        for (j in 0 until newBitmap.height) {
            newBitmap.setPixel(i, j, bitmap.getPixel(i + x, j + y))
        }
    }
    return newBitmap
}
