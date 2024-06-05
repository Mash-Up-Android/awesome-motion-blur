package com.mashup

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.scale
import com.mashup.ui.theme.AwsomeMotionBlurTheme
import toKaleidoscope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AwsomeMotionBlurTheme {
                // A surface container using the 'background' color from the theme
                Test(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun Test(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val compressedWidth = 400
    val compressedHeight= 200
    val originBitmap = context.resources.getDrawable(R.drawable.img_train, null).toBitmap().scale(compressedWidth, compressedHeight)
    val bitmap =
        context.resources.getDrawable(R.drawable.img_train, null).toBitmap().toKaleidoscope(
            widthIntervalAreaSize = 50,
            heightIntervalAreaSize = 200,
            compressedWidth = compressedWidth,
            compressedHeight = compressedHeight
        )
    val image: Bitmap by remember {
        mutableStateOf(
            bitmap
        )
    }

    Box(
        modifier = modifier
    ) {
        KaleidoScopeImage(
            image = image.asImageBitmap(),
            modifier = Modifier
                .size(500.dp)
                .align(Alignment.Center),
            radius = 500f
        ){
            Box(
                contentAlignment = Alignment.Center
            ){
                Image(
                    bitmap = originBitmap.asImageBitmap(),
                    contentDescription = null
                )
                Text(
                    text = "Sample Image",
                    color = Color.White
                )
            }
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
