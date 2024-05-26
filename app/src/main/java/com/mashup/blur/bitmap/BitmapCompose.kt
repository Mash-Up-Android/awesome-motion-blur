package com.mashup.blur.bitmap

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.mashup.blur.common.compose
import com.mashup.blur.common.makeColorInt

fun composeLeftTopBitmap(
    baseBitmap: Bitmap,
    composeBitmap: Bitmap,
    cropRemainArea: Boolean = true  // false : 중첩되어 더해지지 않는 영역 유지
): Bitmap {
    val newBitmap = if (cropRemainArea.not()) {
        baseBitmap.copy(baseBitmap.config, true)
    } else {
        Bitmap.createBitmap(composeBitmap.width, composeBitmap.height, composeBitmap.config)
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
    cropRemainArea: Boolean = true  // false : 중첩되어 더해지지 않는 영역 유지
): Bitmap {
    val newBitmap = if (cropRemainArea.not()) {
        baseBitmap.copy(baseBitmap.config, true)
    } else {
        Bitmap.createBitmap(composeBitmap.width, composeBitmap.height, composeBitmap.config)
    }

    val diffWidth = baseBitmap.width - composeBitmap.width
    val diffHeight = baseBitmap.height - composeBitmap.height
    for (i in 0 until composeBitmap.width) {
        for (j in 0 until composeBitmap.height) {
            newBitmap.setPixel(
                i,
                j,
                baseBitmap.getPixel(i + diffWidth, j + diffHeight) compose composeBitmap.getPixel(i, j)
            )
        }
    }
    return newBitmap
}

fun composeBitmapList(
    bitmapList: List<Bitmap>,
    backgroundColor: Int = Color.WHITE
): Bitmap {
    val newBitmap = Bitmap.createBitmap(bitmapList[0].width, bitmapList[0].height, bitmapList[0].config)
    for (i in 0 until bitmapList[0].width) {
        for (j in 0 until bitmapList[0].height) {
            var r = 0
            var g = 0
            var b = 0
            var count = 0
            var backgroundFlag = false   // 해당 pixel 이 모든 bitmapList 에서 Transparent(0x00000000) 인지 여부
            bitmapList.forEach {
                val currentPixel = it.getPixel(i, j)
                if (currentPixel != 0){
                    count++
                    r += currentPixel.red
                    g += currentPixel.green
                    b += currentPixel.blue
                    backgroundFlag = true
                }
            }
            if (backgroundFlag) {
                if (count != 0) { // divider zero 방지
                    r /= count
                    g /= count
                    b /= count
                }
                newBitmap.setPixel(i, j, makeColorInt(r, g, b))
            } else {
                newBitmap.setPixel(i, j, backgroundColor)
            }
        }
    }
    return newBitmap
}
