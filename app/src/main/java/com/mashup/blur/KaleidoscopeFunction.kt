package com.mashup.blur

import android.graphics.Bitmap
import androidx.annotation.IntRange
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlin.math.max
import kotlin.math.sqrt

fun getKaleidoscopeBitmap(
    bitmap: Bitmap,
    @IntRange(from = 1) interval: Int,
    @IntRange(from = 0) holeRadius: Int = 0,
    backgroundColor: Color = Color.DarkGray
): Bitmap {
    val sideLength = max(bitmap.width, bitmap.height) * 2 + holeRadius * 2
    val newBitmap = Bitmap.createBitmap(sideLength, sideLength, bitmap.config)

    // 원으로 돌릴 1차원 pixel list 생성
    val arrayList = arrayListOf<Int>().apply {
        // 가운데 구멍 만들기
        for (k in 0 until holeRadius*2) {
            add(backgroundColor.toArgb())
        }
        // 각 영역에서 많이 사용된 색으로 변환
        for (k in 0 until (bitmap.width / interval) - 1) {
            val map = hashMapOf<Int, Int>() // HashMap<PixelInt, Count>
            for (i in k * interval until (k + 1) * interval) {
                for (j in 1 until bitmap.height) {
                    val currentPixel = bitmap.getPixel(i, j)
                    map[currentPixel] = map.getOrDefault(currentPixel, 0) + 1
                }
            }
            for (i in 0 until interval) {
                add(map.maxBy { it.value }.key)
            }
        }
    }

    // 원으로 돌리기
    val middlePositionX = newBitmap.width / 2
    val middlePositionY = newBitmap.height / 2
    for (i in 0 until newBitmap.width) {
        for (j in 0 until newBitmap.height) {
            val distance = sqrt(
                (i - middlePositionX) * (i - middlePositionX)
                        + (j - middlePositionY) * (j - middlePositionY).toDouble()
            ).toInt()
            newBitmap.setPixel(i, j, arrayList.getOrElse(distance) { backgroundColor.toArgb() })
        }
    }

    return newBitmap
}
