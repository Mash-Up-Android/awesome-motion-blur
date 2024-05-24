package com.mashup.blur.bitmap

import android.graphics.Bitmap
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

fun rotateBitmap(bitmap: Bitmap, degrees: Float): Bitmap {
    // 회전 각도를 라디안으로 변환
    val radians = Math.toRadians(degrees.toDouble())

    // 원본 Bitmap의 중심점 계산
    val centerX = bitmap.width / 2
    val centerY = bitmap.height / 2

    // 새 Bitmap 생성
    val rotatedBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)

    // 역변환을 사용하여 회전 변환 적용
    for (y in 0 until bitmap.width) {
        for (x in 0 until bitmap.height) {
            // 새로운 좌표를 중심점을 기준으로 이동
            val translatedX = x - centerX
            val translatedY = y - centerY

            // 역변환 적용
            val originalX = translatedX * cos(-radians) - translatedY * sin(-radians)
            val originalY = translatedX * sin(-radians) + translatedY * cos(-radians)

            // 원본 좌표를 중심점 기준으로 다시 이동
            val srcX = (originalX + centerX).roundToInt()
            val srcY = (originalY + centerY).roundToInt()

            // 원본 좌표가 Bitmap의 범위 내에 있는지 확인
            if (srcX in 0 until bitmap.width && srcY in 0 until bitmap.height) {
                rotatedBitmap.setPixel(x, y, bitmap.getPixel(srcX, srcY))
            }
        }
    }

    return rotatedBitmap
}
