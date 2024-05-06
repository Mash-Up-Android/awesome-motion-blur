package com.mashup.blur

import android.graphics.Bitmap

fun getMotionBlurBitmap(bitmap: Bitmap, blurAmount: Int, interval: Int, direction: Direction): Bitmap {
    var composeBitmap = bitmap.copy(bitmap.config, true)

    // Todo : [Refactory] bitmapLeftShift(), composeLeftTopBitmap()을 각각 N번 호출하는 방법은 비효율적이다.
    for (i in 0 until blurAmount) {
        // bitmap (상/하/좌/우) 이동
        val shiftBitmap = when (direction) {
            Direction.LEFT -> bitmapLeftShift(bitmap, interval * i)
            Direction.TOP -> bitmapTopShift(bitmap, interval * i)
            Direction.RIGHT -> bitmapRightShift(bitmap, interval * i)
            Direction.BOTTOM -> bitmapBottomShift(bitmap, interval * i)
        }

        // 원본 bitmap 과 이동시킨 bitmap 합치기
        composeBitmap = when (direction) {
            Direction.LEFT, Direction.TOP ->
                composeLeftTopBitmap(composeBitmap, shiftBitmap)

            Direction.RIGHT, Direction.BOTTOM ->
                composeRightBottomBitmap(composeBitmap, shiftBitmap)
        }
    }
    return composeBitmap
}

enum class Direction {
    LEFT, TOP, RIGHT, BOTTOM
}
