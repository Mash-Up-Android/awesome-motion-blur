package com.mashup.blur

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PixelMap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.platform.LocalContext
import com.google.android.renderscript.Toolkit
import com.mashup.R

@Composable
fun AwesomeMotionBlurImage(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var blurBitmap by remember { mutableStateOf(decodeResourceToBitmap(context)) }
    val pixelMap = blurBitmap.asImageBitmap().toPixelMap()
    val list = getCoordinatesWithColor(pixelMap)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            bitmap = blurBitmap.asImageBitmap(),
            contentDescription = ""
        )
    }
}

fun decodeResourceToBitmap(context: Context): Bitmap {
    val option = BitmapFactory.Options()
    option.inPreferredConfig = Bitmap.Config.ARGB_8888  // Toolkit 사용을 위한 설정

    val bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.img_sample,
        option
    )

    return Toolkit.blur(inputBitmap = bitmap, radius = 20)
}

fun getCoordinatesWithColor(pixelMap: PixelMap): List<Pair<Pixel, String>> {
    val list: MutableList<Pair<Pixel, String>> = mutableListOf()
    for (x in 0..pixelMap.width) {
        for (y in 0..pixelMap.height) {
            val pixel = Pixel(x, y)
            val pixelColor = pixelMap[pixel.x, pixel.y].getColorHexCode()
            list.add(Pair(pixel, pixelColor))
        }
    }
    return list
}

data class Pixel(
    val x: Int,
    val y: Int,
)

fun Int.toHexColorString() = String.format("#%06X", 0xFFFFFFFF and this.toLong())
fun Color.getColorHexCode() = this.toArgb().toHexColorString()
