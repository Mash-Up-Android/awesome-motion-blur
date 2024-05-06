package com.mashup.blur

import android.graphics.Bitmap

fun composeLeftTopBitmap(baseBitmap: Bitmap, composeBitmap: Bitmap): Bitmap {
    val newBitmap = baseBitmap.copy(baseBitmap.config, true)
    
// 이미지 흰 여백 만드는 코드
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

fun composeRightBottomBitmap(baseBitmap: Bitmap, composeBitmap: Bitmap): Bitmap {
    val newBitmap = baseBitmap.copy(baseBitmap.config, true)

// 이미지 흰 여백 만드는 코드
//    val newBitmap = Bitmap.createBitmap(
//        max(baseBitmap.width, composeBitmap.width),
//        max(baseBitmap.height, composeBitmap.height),
//        baseBitmap.config
//    )

    val diffWidth = baseBitmap.width - composeBitmap.width
    val diffHeight = baseBitmap.height - composeBitmap.height
    for (i in newBitmap.width - 1 downTo  diffWidth) {
        for (j in newBitmap.height - 1 downTo  diffHeight) {
            newBitmap.setPixel(
                i,
                j,
                baseBitmap.getPixel(i, j) compose composeBitmap.getPixel(i - diffWidth, j - diffHeight)
            )
        }
    }
    return newBitmap
}
