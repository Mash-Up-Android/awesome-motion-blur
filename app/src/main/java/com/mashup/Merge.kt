package com.mashup

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.MatOfDouble
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

fun test(
    context: Context,
    interval: Int,
    translate: Float,
): Bitmap {
    val imageMatrix = Utils.loadResource(context, R.drawable.img_train)

    val newSize = Size(imageMatrix.cols() / 1.0, imageMatrix.rows() / 1.0)

    val resizedImage = Mat()
    Imgproc.resize(imageMatrix, resizedImage, newSize)

    Log.e("ResizedImage Matrix", resizedImage.toString()) // 리사이즈된 이미지

    val col = resizedImage.cols() + (interval * translate)
    val resultMatrix = Mat(resizedImage.rows(), col.toInt(), resizedImage.type(), Scalar.all(0.0)) // 최종 크기를 계산

    Log.e("Result Matrix", resultMatrix.toString())

    val bitmapConfig = Bitmap.Config.ARGB_8888
    val bitmap = Bitmap.createBitmap(resultMatrix.width(), resultMatrix.height(), bitmapConfig)


    for (i in 0 until interval) {
        val startCol = i * translate
        val endCol = startCol + resizedImage.cols()
        if (endCol > resultMatrix.cols()) {
            break
        }
        val subMatrix = resultMatrix.colRange(startCol.toInt() , endCol.toInt())

        // subMat의 각 픽셀을 반복합니다.
        for (r in 0 until subMatrix.rows()) {
            for (c in 0 until subMatrix.cols()) {
                val pixel = MatOfDouble(*subMatrix.get(r,c))

                // Check if the norm of diff is 0.0
                if (Core.norm(pixel) == 0.0) {
                    subMatrix.put(r, c, *resizedImage.get(r, c))
                } else {
                    // subMat와 smallMat를 가중치를 적용하여 더합니다.
                    val alpha = 0.5 // subMat에 적용할 가중치
                    val beta = 0.5 // smallMat에 적용할 가중치
                    val weightedSum = Mat()
                    Core.addWeighted(subMatrix.row(r).col(c), alpha, resizedImage.row(r).col(c), beta, 0.0, weightedSum)
                    subMatrix.put(r, c, *weightedSum.get(0, 0))
                }
            }
        }
    }

    Utils.matToBitmap(resultMatrix, bitmap)
    return bitmap
}