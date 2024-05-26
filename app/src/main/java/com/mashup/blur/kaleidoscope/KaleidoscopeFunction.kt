package com.mashup.blur.kaleidoscope

import android.graphics.Bitmap
import androidx.annotation.IntRange
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.mashup.blur.bitmap.composeBitmapList
import com.mashup.blur.bitmap.rotateBitmap

fun getKaleidoscopeBitmap(
    bitmap: Bitmap,
    @IntRange(from = 1) bitmapWidthInterval: Int,               // bitmap 가장 많이 사용한 색상 뽑는 width interval
    @IntRange(from = 0, to = 360) degreesInterval: Int = 15,    // 몇 도씩 회전하여 원으로 만들 것인지
    @IntRange(from = 0) holeRadius: Int = 0                     // 중앙 빈 공간 반지름 pixel 값
): Bitmap {
    val composeBitmap = getKaleidoscopeFirstBitmap(bitmap, bitmapWidthInterval, holeRadius)
    val list = mutableListOf<Bitmap>().apply {
        for (i in degreesInterval..360 step degreesInterval) {
            add(rotateBitmap(composeBitmap, i.toFloat()))
        }
    }
    val newBitmap = composeBitmapList(list)
    composeBitmap.recycle()
    list.forEach { it.recycle() }
    return newBitmap
}

/**
 * Kaleidoscope 를 원으로 돌려가면서 다하기 전 초기의 이미지 생성 함순
 */
private fun getKaleidoscopeFirstBitmap(
    bitmap: Bitmap,
    @IntRange(from = 1) interval: Int,
    @IntRange(from = 0) holeRadius: Int = 0
): Bitmap {
    // 원으로 돌릴 1차원 pixel list 생성
    val arrayList = arrayListOf<Int>().apply {
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
        // 가운데 구멍 만들기
        for (k in 0 until holeRadius * 2) {
            add(Color.Transparent.toArgb())
        }
    }

    val newBitmap = Bitmap.createBitmap(arrayList.size * 2, arrayList.size * 2, bitmap.config)
    val middleOfHeight = (newBitmap.height - bitmap.height) / 2
    for (i in 0 until newBitmap.width / 2) {
        for (j in middleOfHeight until middleOfHeight + bitmap.height) {
            newBitmap.setPixel(i, j, arrayList[i])
        }
    }
    return newBitmap
}
