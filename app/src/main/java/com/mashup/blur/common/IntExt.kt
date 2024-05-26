package com.mashup.blur.common

import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red

internal infix fun Int.compose(value: Int): Int {
    return makeColorInt(
        r = (this.red + value.red) / 2,
        g = (this.green + value.green) / 2,
        b = (this.blue + value.blue) / 2,
        a = (this.alpha + value.alpha) / 2
    )
}

fun makeColorInt(r: Int, g: Int, b: Int, a: Int = 0xFF) = b or (g shl 8) or (r shl 16) or (a shl 24)

fun Int.toHexColorString() = String.format("#%08X", 0xFFFFFFFF and this.toLong())
