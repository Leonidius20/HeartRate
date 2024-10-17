package io.github.leonidius20.heartrate.ui.result

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.leonidius20.heartrate.data.measurements.MeasurementEntity
import io.github.leonidius20.heartrate.ui.common.BoxWithCircleBackground
import io.github.leonidius20.heartrate.ui.theme.btnColor
import io.github.leonidius20.heartrate.ui.theme.heartRateFast
import io.github.leonidius20.heartrate.ui.theme.heartRateNormal
import io.github.leonidius20.heartrate.ui.theme.heartRateSlowed
import io.github.leonidius20.heartrate.ui.theme.resultTitleTextStyle
import io.github.leonidius20.heartrate.ui.theme.resultTypeTextStyle
import io.github.leonidius20.heartrate.ui.theme.topBarColor
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeasurementResultScreen(
    backToHomepage: () -> Unit,
) {
    val viewModel = hiltViewModel<MeasurementResultViewModel>()

    val measurement by viewModel.measurement.collectAsStateWithLifecycle()

    MeasurementResultScreen(
        backToHomepage = backToHomepage,
        measurement = measurement,
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MeasurementResultScreen(
    backToHomepage: () -> Unit,
    measurement: MeasurementEntity?,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
        ,
        topBar = {
            TopAppBar(
                actions = {
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color.Transparent,
                        )
                    ) {
                        Row {
                            Text("History")
                            Icon(Icons.Default.Refresh, contentDescription = null)
                        }
                    }
                },
                title = { Text("Result") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topBarColor,
                    titleContentColor = Color.White,
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            BoxWithCircleBackground(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {

                if (measurement != null) {
                    ResultCard(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(width = 351.dp, height = 252.dp),
                        bpm = measurement.bpm,
                        timestamp = measurement.timestamp,
                    )
                }
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = btnColor),
                onClick = {
                    backToHomepage()
                }
            ) {
                Text(
                    text = "Done"
                )
            }
        }


    }
}

@Composable
@Preview(widthDp = 393, heightDp = 852)
private fun MeasurementResultScreenPreview() {
    MeasurementResultScreen(
        backToHomepage = {},
        measurement = MeasurementEntity(bpm = 100, timestamp = 1729023033)
    )
}

@Composable
private fun ResultCard(
    modifier: Modifier,
    bpm: Int,
    timestamp: Long,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        ConstraintLayout(
            Modifier.fillMaxSize()
        ) {
            val instant = Instant
                .fromEpochMilliseconds(timestamp)
                .toLocalDateTime(TimeZone.currentSystemDefault())
            val timeStr = "${instant.hour}:${instant.minute}"
            val dateStr = "${instant.dayOfMonth}/${instant.monthNumber}/${instant.year}"

            val (
                time, date, timeIcon,
                resultTitle, resultType,
                resultBar, referenceValuesList,
            ) = createRefs()

            val timeIconAndTextBarrier = createStartBarrier(time, date)

            Text(
                text = timeStr,
                modifier = Modifier.constrainAs(time) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
            )

            Text(
                text = dateStr,
                modifier = Modifier.constrainAs(date) {
                    top.linkTo(time.bottom)
                    end.linkTo(parent.end)
                }
            )

            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier.constrainAs(timeIcon) {
                    end.linkTo(timeIconAndTextBarrier)
                    top.linkTo(parent.top)
                },
            )

            Text(
                text = "Your result",
                style = resultTitleTextStyle,
                modifier = Modifier.constrainAs(resultTitle) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
            )

            val (resultTypeStr, color) = when(bpm) {
                in 0 until 60 -> "Slowed" to heartRateSlowed
                in 60..100 -> "Normal" to heartRateNormal
                in 100 until Int.MAX_VALUE -> "Sped up" to heartRateFast
                else -> "Error" to Color.Red
            }

            Text(
                text = resultTypeStr,
                style = resultTypeTextStyle,
                color = color,
                modifier = Modifier.constrainAs(resultType) {
                    top.linkTo(resultTitle.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                }
            )

            BpmValueBar(
                bpm = bpm,
                modifier = Modifier.constrainAs(resultBar) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(resultType.bottom, margin = 8.dp)
                }
            )

        }


    }
}

@Composable
private fun BpmValueBar(
    modifier: Modifier,
    bpm: Int,
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(20.dp)
            .clip(RoundedCornerShape(size = 8.dp)),
    ) {
        val scope = this
        val totalWidth = scope.maxWidth
        val thirdOfWidth = totalWidth / 3

        val density = LocalDensity.current
        val layoutDirection = LocalLayoutDirection.current

        Canvas(
            Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .height(12.dp)
        ) {
            // val maxLength = size.width
            drawOutline(
                RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp).createOutline(
                    size = size.copy(width = thirdOfWidth.toPx()),
                    density = density,
                    layoutDirection = layoutDirection,
                ),
                color = heartRateSlowed,
            )

            drawRect(
                color = heartRateNormal,
                topLeft = Offset.Zero.copy(x = thirdOfWidth.toPx()),
                size = size.copy(width = thirdOfWidth.toPx()),
            )

            translate(left = thirdOfWidth.toPx() * 2) {
                drawOutline(
                    outline = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp).createOutline(
                        size = size.copy(width = thirdOfWidth.toPx()),
                        layoutDirection = layoutDirection,
                        density = density,
                    ),
                    color = heartRateFast,
                )
            }
        }
    }
}