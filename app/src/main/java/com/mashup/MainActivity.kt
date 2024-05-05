package com.mashup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.mashup.blur.Direction
import com.mashup.blur.getBitmapByDrawable
import com.mashup.blur.getMotionBlurBitmap
import com.mashup.ui.theme.AwsomeMotionBlurTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AwsomeMotionBlurTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val bitmap = getBitmapByDrawable(context, R.drawable.small_image)

    bitmap.asImageBitmap()

    Image(
        modifier = modifier,
        contentDescription = "",
        bitmap = getMotionBlurBitmap(
            bitmap = bitmap,
            blurAmount = 3,
            interval = 30,
            direction = Direction.LEFT
        ).asImageBitmap()
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AwsomeMotionBlurTheme {
        Greeting("Android")
    }
}
