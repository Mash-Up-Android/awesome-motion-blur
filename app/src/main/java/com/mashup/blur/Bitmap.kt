package com.mashup.blur

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.res.imageResource

fun getBitmapByDrawable(context: Context, @DrawableRes resId: Int): Bitmap {
    val bitmap = ImageBitmap.imageResource(context.resources, resId).asAndroidBitmap()
    return bitmap.copy(bitmap.config, true)
}
