package io.github.leonidius20.heartrate.ui.splash

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    Column(
        Modifier.safeDrawingPadding()
    ) {
        BoxWithCircleBackground(
            Modifier
                .weight(1f, fill = true)
        ) {
            Column(
                Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
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
            modifier = Modifier
                .padding(top = 32.dp, bottom = 32.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.CenterHorizontally),
            progress = { progress }
        )
    }


    // animation progress
}

@Composable
fun ProgressBar(
    modifier: Modifier = Modifier,
    progress: () -> Float
) {
    val progressBarShape = RoundedCornerShape(7.dp)

    Box(
        modifier
            .fillMaxWidth()
            .height(14.dp)
            .clip(progressBarShape)
            .background(Color(0xFFFFACAC))
            .border(1.dp, Color(0xFFFF4B4B), progressBarShape),
    ) {
        // filled portion
        Canvas(
            Modifier
                .fillMaxWidth()
                .height(14.dp)
        ) {
            val maxLength = size.width
            val length = maxLength * progress()

            drawRoundRect(
                color = Color(0xFFFF6B6B),
                cornerRadius = CornerRadius(x = 7.dp.toPx(), y = 7.dp.toPx()),
                size = size.copy(width = length)
            )
        }

        val progressPercent = (progress() * 100).toInt()

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "$progressPercent%",
            fontSize = 8.sp,
            color = Color.White,
        )

    }
}