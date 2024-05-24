package com.mashup.blur.bitmap

import android.graphics.Bitmap

fun bitmapLeftShift(bitmap: Bitmap, x: Int): Bitmap = bitmapLeftTopShift(bitmap, x, 0)

fun bitmapTopShift(bitmap: Bitmap, y: Int): Bitmap = bitmapLeftTopShift(bitmap, 0, y)

fun bitmapRightShift(bitmap: Bitmap, x: Int): Bitmap = bitmapRightBottomShift2(bitmap, x, 0)

fun bitmapBottomShift(bitmap: Bitmap, y: Int): Bitmap = bitmapRightBottomShift2(bitmap, 0, y)

fun bitmapLeftTopShift(bitmap: Bitmap, x: Int, y: Int): Bitmap {
    val newBitmap = Bitmap.createBitmap(bitmap.width - x, bitmap.height - y, bitmap.config)
    for (i in 0 until newBitmap.width) {
        for (j in 0 until newBitmap.height) {
            newBitmap.setPixel(i, j, bitmap.getPixel(i + x, j + y))
        }
    }
    return newBitmap
}

fun bitmapRightBottomShift2(bitmap: Bitmap, x: Int, y: Int): Bitmap {
    val newBitmap = Bitmap.createBitmap(bitmap.width - x, bitmap.height - y, bitmap.config)
    for (i in 0 until newBitmap.width) {
        for (j in 0 until newBitmap.height) {
            newBitmap.setPixel(i, j, bitmap.getPixel(i, j))
        }
    }
    return newBitmap
}
