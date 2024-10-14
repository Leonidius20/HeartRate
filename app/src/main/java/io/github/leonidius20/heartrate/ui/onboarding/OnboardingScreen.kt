package io.github.leonidius20.heartrate.ui.onboarding

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.leonidius20.heartrate.R
import io.github.leonidius20.heartrate.ui.theme.bgCircleColor
import io.github.leonidius20.heartrate.ui.theme.btnColor
import io.github.leonidius20.heartrate.ui.theme.onboardingDescription
import io.github.leonidius20.heartrate.ui.theme.onboardingTitle
import kotlinx.coroutines.launch
import kotlin.math.abs
import androidx.compose.ui.graphics.lerp as colorLerp
import androidx.compose.ui.unit.lerp as dpLerp

@Composable
@Preview(widthDp = 393, heightDp = 852)
fun OnboardingScreen() {
    Column(
        Modifier.drawBehind {
            drawLine(
                color = Color.Black,
                start = center.copy(y = 0f),
                end = center.copy(y = size.height),
                strokeWidth = 1.dp.toPx(),
            )
        }
    ) {
        val pageCount = 3

        val pagerState = rememberPagerState(pageCount = { pageCount })

        BoxWithConstraints(
            Modifier.weight(1f),
        ) {
            val scope = this

            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .drawBehind {
                        // todo: better values esp. in horizontal mode
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

        // Row of page indicators
        Row(Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 16.dp, bottom = 16.dp)
        ) {
            val currentPage = pagerState.currentPage
            val offsetFromCurrent = pagerState.currentPageOffsetFraction

            val currentPageProgress = 1 - abs(offsetFromCurrent)
            val prevPageProgress = if (offsetFromCurrent < 0)
                abs(offsetFromCurrent) else 0F
            val nextPageProgress = if (offsetFromCurrent > 0)
                offsetFromCurrent else 0F

            repeat(pageCount) { pageIndex ->
                val progress = when(pageIndex) {
                    currentPage -> currentPageProgress
                    currentPage - 1 -> prevPageProgress
                    currentPage + 1 -> nextPageProgress
                    else -> 0F
                }
                PageIndicator(progressToActive = progress)
                if (pageIndex < pageCount - 1) {
                    Spacer(Modifier.width(16.dp))
                }
            }
        }

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
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = title,
            style = onboardingTitle,
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = description,
            style = onboardingDescription,
            // textAlign = TextAlign.Center,
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

@Composable
@Preview
private fun PageIndicator(
    /**
     * from 0.0 to 1.0, how close should this indicator be to the active state
     */
    progressToActive: Float = 0.0F,
) {
    val defaultWidth = 14.dp // width if not activated
    val maxWidth = 44.dp // when active

    val height = 14.dp

    val inactiveColor = Color(0xFFE6E6E6)
    val activeColor = Color(0xFFFF6B6B)

    val color = colorLerp(inactiveColor, activeColor, progressToActive)
    val width = dpLerp(defaultWidth, maxWidth, progressToActive)

    // todo: fix recompositions
    // todo: make sure the center pill is in the center
    Canvas(
        modifier = Modifier

            .width(width)
            .height(height)
            // .padding(start = 32.dp, end = 32.dp)
    ) {
        drawRoundRect(
            color = color,
            size = Size(
                width = width.toPx(),
                height = height.toPx(),
            ),
            cornerRadius = CornerRadius(
                x = defaultWidth.toPx(),
                y = height.toPx(),
            )
        )
    }
}