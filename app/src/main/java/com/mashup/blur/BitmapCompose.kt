package com.mashup.blur

import android.graphics.Bitmap
import kotlin.math.max

fun composeLeftTopBitmap(
    baseBitmap: Bitmap,
    composeBitmap: Bitmap,
    cropRemainArea: Boolean = true  // false : 중첩되어 더해지지 않는 영역 제거
): Bitmap {
    val newBitmap = if (cropRemainArea.not()) {
        baseBitmap.copy(baseBitmap.config, true)
    } else {
        Bitmap.createBitmap(
            max(composeBitmap.width, composeBitmap.width),
            max(composeBitmap.height, composeBitmap.height),
            composeBitmap.config
        )
    }

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

fun composeRightBottomBitmap(
    baseBitmap: Bitmap,
    composeBitmap: Bitmap,
    cropRemainArea: Boolean = false  // false : 중첩되어 더해지지 않는 영역 제거
): Bitmap {
    val newBitmap = if (cropRemainArea.not()) {
        baseBitmap.copy(baseBitmap.config, true)
    } else {
        Bitmap.createBitmap(
            max(composeBitmap.width, composeBitmap.width),
            max(composeBitmap.height, composeBitmap.height),
            composeBitmap.config
        )
    }

    val diffWidth = baseBitmap.width - composeBitmap.width
    val diffHeight = baseBitmap.height - composeBitmap.height
    for (i in newBitmap.width - 1 downTo diffWidth) {
        for (j in newBitmap.height - 1 downTo diffHeight) {
            newBitmap.setPixel(
                i,
                j,
                baseBitmap.getPixel(i, j) compose composeBitmap.getPixel(
                    i - diffWidth,
                    j - diffHeight
                )
            )
        }
    }
    return newBitmap
}
