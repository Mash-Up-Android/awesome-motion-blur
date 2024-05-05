package com.mashup.blur

internal infix fun Int.compose(value: Int): Int {
    fun Int.getAlpha() = (this shr 24 and 0xFF)
    fun Int.getRed() = (this shr 16 and 0xFF)
    fun Int.getGreen() = (this shr 8 and 0xFF)
    fun Int.getBlue() = (this shr 0 and 0xFF)

    return (((this.getAlpha() + value.getAlpha()) / 2) shl 24) or
            (((this.getRed() + value.getRed()) / 2) shl 16) or
            (((this.getGreen() + value.getGreen()) / 2) shl 8) or
            (((this.getBlue() + value.getBlue()) / 2) shl 0)
}

fun Int.toHexColorString() = String.format("#%08X", 0xFFFFFFFF and this.toLong())
