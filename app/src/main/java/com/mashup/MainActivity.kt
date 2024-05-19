package com.mashup

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.drawable.toBitmap
import com.mashup.ui.theme.AwsomeMotionBlurTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

@OptIn(ExperimentalComposeApi::class)
@Composable
fun Test(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val bitmap = context.resources.getDrawable(R.drawable.img_train, null).toBitmap()
    var image: Bitmap? by remember {
        mutableStateOf(
            bitmap
        )
    }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            image = test(context = context, interval = 3, translate = 200f)
            Log.e("Image :::", "$image")
        }
    }

    Box(
        modifier = modifier.background(color = Color.Green),
    ) {
        image?.let {
            Log.e("Image :::", "${it.width} ${it.height}")
            Image(
                bitmap = it.asImageBitmap(),
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
