package io.github.leonidius20.heartrate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.github.leonidius20.heartrate.ui.theme.HeartRateTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HeartRateTheme {
                NavGraph()
            }
        }
    }

}