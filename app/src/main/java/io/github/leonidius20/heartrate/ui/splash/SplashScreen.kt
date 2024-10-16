package io.github.leonidius20.heartrate.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.leonidius20.heartrate.ui.common.BoxWithCircleBackground
import kotlinx.coroutines.delay
import io.github.leonidius20.heartrate.R
import io.github.leonidius20.heartrate.ui.theme.appNameTextStyle

@Composable
@Preview(widthDp = 393, heightDp = 852)
fun SplashScreen(
    onAnimationFinished: () -> Unit = {},
) {
    var progress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        repeat(10) { iteration ->
            progress = iteration / 9f
            delay(100)
        }

        onAnimationFinished()
    }

    Column {
        BoxWithCircleBackground(
            Modifier
                .weight(1f, fill = true)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                Image(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    painter = painterResource(R.drawable.heart_illustration),
                    contentDescription = null,
                )

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.app_name),
                    style = appNameTextStyle,
                )
            }
        }

        ProgressBar(
            progress = { progress }
        )
    }



    // animation progress
}

@Composable
fun ProgressBar(progress: () -> Float) {
    val progressPercent = (progress() * 100).toInt()
    Text("progress $progressPercent")
}