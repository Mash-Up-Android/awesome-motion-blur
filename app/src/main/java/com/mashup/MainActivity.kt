package com.mashup

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.renderscript.Toolkit
import com.mashup.ui.theme.AwsomeMotionBlurTheme
import dev.shreyaspatil.capturable.capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.delay
import org.opencv.android.OpenCVLoader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        OpenCVLoader.initLocal()
        setContent {
            AwsomeMotionBlurTheme {
                // A surface container using the 'background' color from the theme
                Test(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeApi::class)
@Composable
fun Test(
    modifier: Modifier = Modifier,
) {
    val resource = LocalContext.current.resources

    var bitmap: ImageBitmap? by remember { mutableStateOf(null) }

    val context = LocalContext.current
    val image: Bitmap by remember {
        mutableStateOf(
//            BitmapFactory.decodeResource(resource, R.drawable.cat),
            merge(context),
        )
    }

    val captureController = rememberCaptureController()

    LaunchedEffect(Unit) {
        delay(3_000)
        val bitmapAsync = captureController.captureAsync(
            config = Bitmap.Config.ARGB_8888,
        )
        try {
            bitmap = bitmapAsync.await()
            // Do something with `bitmap`.
        } catch (error: Throwable) {
            // Error occurred, do something.
            Log.e("Error:", error.message ?: "")
        }
    }

    val pixelMap = image.asImageBitmap().toPixelMap()

    Column {
        Canvas(
            modifier = Modifier.width((pixelMap.width * 3).dp).height(pixelMap.height.dp).capturable(captureController),
        ) {
            /**
             *  내가 원하는 건
             *  pixelMap.width / 2  ~ pixelMap.width 우측
             *  0 ~ pixelMap.width / 2
             *
             *  0 ~ pixelMap.height
             *
             */

            val imageBitmap = image.asImageBitmap()
            merge(pixelMap)
            Log.e("PixelMap Size:::", "${pixelMap.width} ${pixelMap.height} ${pixelMap.buffer}")

            drawImage(imageBitmap)

            translate(left = 100f) {
                drawImage(imageBitmap)
            }
            translate(left = 200f) {
                drawImage(imageBitmap)
            }
        }

        Spacer(
            modifier = Modifier.height(100.dp),
        )

        bitmap?.let {
//            fastblur(
//                it.asAndroidBitmap().copy(
//                    Bitmap.Config.ARGB_8888,
//                    true,
//                ),
//                radius = 5,
//                scale = 1f,
//            )?.asImageBitmap()?.let { it1 ->
//                Image(
//                    modifier= Modifier.width((pixelMap.width * 3).dp).height(pixelMap.height.dp),
//                    bitmap = it1,
//                    contentDescription = null,
//                    contentScale = ContentScale.Fit
//                )
//            }
            Image(
                bitmap = Toolkit.blur(it.asAndroidBitmap().copy(Bitmap.Config.ARGB_8888, true)).asImageBitmap(),
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTest() {
    AwsomeMotionBlurTheme {
        Test(
            modifier = Modifier.wrapContentSize(),
        )
    }
}
