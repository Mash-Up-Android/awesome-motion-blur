package com.mashup

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun KaleidoScopeImage(
    modifier: Modifier = Modifier,
    image: ImageBitmap? = null,
    repeatCount: Int = 50,
    radius: Float = 100f,
    innerContents: @Composable () -> Unit ={},
) {
    if (image == null) return
    val circleRadius = 50.dp.toPx()
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val createdBitmapInCanvas = Bitmap.createBitmap(size.width.toInt(), size.height.toInt(), Bitmap.Config.ARGB_8888)
            val bitmap = image.asAndroidBitmap()
            val imageWidth = image.width.toFloat()
            val imageHeight = image.height.toFloat()

            // 캔버스의 중앙 계산
            val centerX = size.width / 2
            val centerY = size.height / 2

            // 원형으로 이미지 배치
            repeat(times = repeatCount) {
                val angle = it * (360f / repeatCount) // 돌아가려는 각도
                val radians = Math.toRadians(angle.toDouble()) // 라디안 각의 크기를 재는 단위
                val offsetX = (radius * cos(radians)).toFloat()
                val offsetY = (radius * sin(radians)).toFloat()

                for (x in 0 until bitmap.width) {
                    for (y in 0 until bitmap.height) {
                        val pixel = bitmap.getPixel(x, y)

                        val transformedX = (centerX + offsetX + (x - imageWidth / 2) * cos(radians) - (y - imageHeight / 2) * sin(radians)).toInt()
                        val transformedY = (centerY + offsetY + (x - imageWidth / 2) * sin(radians) + (y - imageHeight / 2) * cos(radians)).toInt()

                        // 변환된 좌표가 비트맵 범위 내에 있는지 확인
                        if (transformedX in 0 until createdBitmapInCanvas.width && transformedY in 0 until createdBitmapInCanvas.height) {
                            val existingPixel = createdBitmapInCanvas.getPixel(transformedX, transformedY)

                            val newPixel = blendPixels(existingPixel, pixel, 0.5f)
                            createdBitmapInCanvas.setPixel(transformedX, transformedY, newPixel)
                        }
                    }
                }
            }
            drawImage(createdBitmapInCanvas.asImageBitmap())
            // 중앙에 원형 그리기
            drawCircle(
                color = Color.White, // 원형의 색상을 변경할 수 있습니다.
                radius = circleRadius,
                center = Offset(centerX, centerY)
            )
        }
        Box(
            modifier= Modifier.align(Alignment.Center)
        ){
            innerContents()
        }

    }
}
fun blendPixels(pixel1: Int, pixel2: Int, alpha: Float): Int {
    val alpha1 = android.graphics.Color.alpha(pixel1)
    val red1 = android.graphics.Color.red(pixel1)
    val green1 = android.graphics.Color.green(pixel1)
    val blue1 = android.graphics.Color.blue(pixel1)

    val alpha2 = android.graphics.Color.alpha(pixel2)
    val red2 = android.graphics.Color.red(pixel2)
    val green2 = android.graphics.Color.green(pixel2)
    val blue2 = android.graphics.Color.blue(pixel2)

    val outAlpha = ((1 - alpha) * alpha1 + alpha * alpha2).toInt()
    val outRed = ((1 - alpha) * red1 + alpha * red2).toInt()
    val outGreen = ((1 - alpha) * green1 + alpha * green2).toInt()
    val outBlue = ((1 - alpha) * blue1 + alpha * blue2).toInt()

    return android.graphics.Color.argb(outAlpha, outRed, outGreen, outBlue)
}
@Composable
fun Dp.toPx(): Float {
    return with(LocalDensity.current) { this@toPx.toPx() }
}