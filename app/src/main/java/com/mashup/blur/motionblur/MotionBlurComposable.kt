package com.mashup.blur.motionblur

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.annotation.IntRange
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.mashup.blur.bitmap.getBitmapByDrawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MotionBlurImage(
    modifier: Modifier,
    @DrawableRes resId: Int,
    @IntRange(from = 0) blurAmount: Int,
    @IntRange(from = 0) interval: Int,
    direction: Direction,
    indicator: @Composable (Modifier) -> Unit = { }
) {
    val context = LocalContext.current
    val bitmap = getBitmapByDrawable(context, resId)

    // 비동기로 KaleidoscopeBitmap을 생성하고 상태를 유지
    val motionBlurBitmap by produceState<Bitmap?>(null) {
        withContext(Dispatchers.Default) {
            value = getMotionBlurBitmap(
                bitmap = bitmap,
                blurAmount = blurAmount,
                interval = interval,
                direction = direction
            )
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        motionBlurBitmap?.let {
            Image(
                modifier = modifier,
                contentDescription = "",
                bitmap = it.asImageBitmap()
            )
        } ?: run {
            indicator(modifier)
        }
    }
}
