package io.github.leonidius20.heartrate.ui.onboarding

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.leonidius20.heartrate.ui.theme.bgCircleColor
import io.github.leonidius20.heartrate.ui.theme.btnColor
import io.github.leonidius20.heartrate.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import kotlinx.coroutines.launch

@Composable
@Preview(widthDp = 393, heightDp = 852)
fun OnboardingScreen() {
    Column {
        val pagerState = rememberPagerState(pageCount = { 3 })

        BoxWithConstraints(
            Modifier.weight(1f),
        ) {
            val scope = this

            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .drawBehind {
                        // todo: better values
                        val centerUpwardOffset = 100.dp.toPx()

                        drawCircle(
                            color = bgCircleColor,
                            radius = (scope.maxHeight / 2).toPx() + centerUpwardOffset, // radius = from circle center (center of pager? up to the end of pager)
                            center = center.copy(y = center.y - centerUpwardOffset)
                        )
                    },
                state = pagerState,
            ) { page ->
                when (page) {
                    0 -> OnboardingPageOne()
                    1 -> OnboardingPageTwo()
                    2 -> OnboardingPageThree()
                }
            }
        }

        val isOnFirstOrLastPage by remember {
            derivedStateOf {
                pagerState.currentPage == 0
                        || pagerState.currentPage == pagerState.pageCount - 1
            }
        }

        //val freshCurrentPageValue = rememberUpdatedState {
        //    pagerState
        //}

        val scope = rememberCoroutineScope()

        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = btnColor),
            onClick = {
                if (pagerState.currentPage < pagerState.pageCount - 1) {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                } else {
                    // navigate
                }


            }
        ) {
            val text = if (isOnFirstOrLastPage) "Start" else "Continue"

            Text(
                text = text
            )
        }
    }
}

@Composable
private fun OnboardingPage(
    imagePainter: Painter,
    title: String,
    description: String,
) {
    Column {
        Image(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            painter = imagePainter,
            contentDescription = null,
        )
        Spacer(Modifier.height(100.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun OnboardingPageOne() {
    OnboardingPage(
        imagePainter = painterResource(R.drawable.onboarding_pic_one),
        title = "Your pressure tracker",
        description = "Зазначайте, відстежуйте та аналізуйте свої показники артеріального тиску."
    )
}

@Composable
private fun OnboardingPageTwo() {
    Column {

    }
}

@Composable
private fun OnboardingPageThree() {
    Column {

    }
}