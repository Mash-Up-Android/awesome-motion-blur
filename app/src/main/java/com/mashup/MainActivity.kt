package com.mashup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
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
import com.mashup.blur.kaleidoscope.KaleidoscopeImage
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
    var isShow by rememberSaveable { mutableStateOf(false) }
    if (isShow.not()) {
        Button(onClick = { isShow = isShow.not() }) { Text(text = "누르세요" )}
    }

    if (isShow) {
        KaleidoscopeImage(
            modifier = modifier.fillMaxSize(),
            resId = R.drawable.small_image,
            indicator = { CircularProgressIndicator(it.wrapContentSize()) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AwsomeMotionBlurTheme {
        Greeting("Android")
    }
}
