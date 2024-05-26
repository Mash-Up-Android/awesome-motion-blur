package com.mashup.blur.bitmap

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.res.imageResource

fun getBitmapByDrawable(context: Context, @DrawableRes resId: Int): Bitmap =
    ImageBitmap.imageResource(context.resources, resId).asAndroidBitmap()
