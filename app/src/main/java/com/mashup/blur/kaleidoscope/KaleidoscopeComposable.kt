package com.mashup.blur.kaleidoscope

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
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
fun KaleidoscopeImage(
    modifier: Modifier = Modifier,
    @DrawableRes resId: Int,
    indicator: @Composable (Modifier) -> Unit = { }
) {
    val context = LocalContext.current
    val bitmap = getBitmapByDrawable(context, resId)

    // 비동기로 KaleidoscopeBitmap을 생성하고 상태를 유지
    val kaleidoscopeBitmap by produceState<Bitmap?>(null) {
        withContext(Dispatchers.Default) {
            value = getKaleidoscopeBitmap(
                bitmap = bitmap,
                bitmapWidthInterval = 10,
                degreesInterval = 15,
                holeRadius = 30,
            )
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        kaleidoscopeBitmap?.let {
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
