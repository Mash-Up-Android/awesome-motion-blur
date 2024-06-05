import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.scale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * HeightIntervalAreaSize == compressdHeight
 */
fun Bitmap.toKaleidoscope(
    heightIntervalAreaSize: Int,
    widthIntervalAreaSize: Int,
    compressedHeight: Int,
    compressedWidth: Int
): Bitmap {

    require(heightIntervalAreaSize == compressedHeight){
        "require heightIntervalAreaSize == compressedHeight"
    }
    // Scale the bitmap
    val scaledBitmap = this.scale(compressedWidth, compressedHeight)

    // Iterate over the scaled bitmap in intervals
    runBlocking(Dispatchers.Default) {
        for (i in 0 until scaledBitmap.height step heightIntervalAreaSize) {
            for (j in 0 until scaledBitmap.width step widthIntervalAreaSize) {
                // Launch a coroutine for each area
                launch {
                    // Initialize variables for calculating average
                    var totalRed = 0
                    var totalGreen = 0
                    var totalBlue = 0
                    var pixelCount = 0

                    // Iterate over each area
                    for (x in i until (i + heightIntervalAreaSize).coerceAtMost(scaledBitmap.height)) {
                        for (y in j until (j + widthIntervalAreaSize).coerceAtMost(scaledBitmap.width)) {
                            // Get the pixel value
//                             Log.e("LOG :::", "i = $i j = $j x = $x y = $y width = ${scaledBitmap.width} height = ${scaledBitmap.height}")
                            val pixel =
                                scaledBitmap.getPixel(y,x )

                            // Add the RGB values to the total
                            totalRed += Color.red(pixel)
                            totalGreen += Color.green(pixel)
                            totalBlue += Color.blue(pixel)

                            pixelCount++
                        }
                    }

                    // Calculate the average RGB values
                    val averageRed = totalRed / pixelCount
                    val averageGreen = totalGreen / pixelCount
                    val averageBlue = totalBlue / pixelCount

                    // Create a color with the average RGB values
                    val averageColor = Color.rgb(averageRed, averageGreen, averageBlue)

                    // Apply the average color to all pixels in the area
                    for (x in i until (i + heightIntervalAreaSize).coerceAtMost(scaledBitmap.height)) {
                        for (y in j until (j + widthIntervalAreaSize).coerceAtMost(scaledBitmap.width)) {
                            scaledBitmap.setPixel(
                                y,
                                x,
                                averageColor
                            )
                        }
                    }
                }
            }
        }
    }
    return scaledBitmap
}