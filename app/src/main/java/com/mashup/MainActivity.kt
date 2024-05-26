package com.mashup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.blur.kaleidoscope.KaleidoscopeImage
import com.mashup.blur.motionblur.Direction
import com.mashup.blur.motionblur.MotionBlurImage
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
                    SampleScreen("Android")
                }
            }
        }
    }
}

@Composable
fun SampleScreen(name: String, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    var isShow by rememberSaveable { mutableStateOf(false) }
    if (isShow.not()) {
        Button(
            modifier = modifier.wrapContentSize(),
            onClick = { isShow = isShow.not() }
        ) {
            Text(text = "누르세요")
        }
    }

    if (isShow) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            KaleidoscopeImage(
                modifier = modifier.size(500.dp, 500.dp),
                resId = R.drawable.small_image,
                indicator = { CircularProgressIndicator(it.wrapContentSize()) }
            )
            MotionBlurImage(
                modifier = modifier.size(500.dp, 500.dp),
                resId = R.drawable.small_image,
                blurAmount = 3,
                interval = 10,
                direction = Direction.RIGHT,
                indicator = { CircularProgressIndicator(it.wrapContentSize()) }
            )
            MotionBlurImage(
                modifier = modifier.size(500.dp, 500.dp),
                resId = R.drawable.small_image,
                blurAmount = 3,
                interval = 10,
                direction = Direction.LEFT,
                indicator = { CircularProgressIndicator(it.wrapContentSize()) }
            )
            MotionBlurImage(
                modifier = modifier.size(500.dp, 500.dp),
                resId = R.drawable.small_image,
                blurAmount = 3,
                interval = 10,
                direction = Direction.TOP,
                indicator = { CircularProgressIndicator(it.wrapContentSize()) }
            )
            MotionBlurImage(
                modifier = modifier.size(500.dp, 500.dp),
                resId = R.drawable.small_image,
                blurAmount = 3,
                interval = 10,
                direction = Direction.BOTTOM,
                indicator = { CircularProgressIndicator(it.wrapContentSize()) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AwsomeMotionBlurTheme {
        SampleScreen("Android")
    }
}
