package io.github.leonidius20.heartrate.ui.measure

import android.view.SurfaceView
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import io.github.leonidius20.heartrate.ui.common.BoxWithCircleBackground
import net.kibotu.heartrateometer.HeartRateOmeter
import io.github.leonidius20.heartrate.R
import io.github.leonidius20.heartrate.ui.theme.heartRateTextStyle
import io.github.leonidius20.heartrate.ui.theme.onboardingDescription


@OptIn(ExperimentalPermissionsApi::class)
@Preview(widthDp = 393, heightDp = 852)
@Composable
fun MeasureScreen(
    backToHomePage: () -> Unit = {},
) {
    BoxWithCircleBackground {
        Column(
            Modifier
                .safeDrawingPadding()
                .fillMaxSize()
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.End),
                onClick = { backToHomePage() }
            ) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }

            val surfaceView = rememberSurfaceView(
                widthPx = 42.dp.dpToPx().toInt(),
                heightPx = 42.dp.dpToPx().toInt(),
            )

            val cameraPermission = rememberPermissionState(
                android.Manifest.permission.CAMERA
            )

            if (cameraPermission.status.isGranted) {
                var fingerDetected by remember { mutableStateOf(false) }


                // todo: remember {} this
                val meter = remember {
                    HeartRateOmeter()
                        .withAverageAfterSeconds(3)
                        .setFingerDetectionListener { detected ->
                            fingerDetected = detected
                        }
                        .bpmUpdates(surfaceView)
                }

                val heartRate by meter
                    /*.subscribe {
                        if (it.value == 0)
                            return@subscribe

                        m.set(0, 0, it.value.toDouble())

                        // state [x, dx]
                        val s = kalman.Predict()

                        // corrected state [x, dx]
                        val c = kalman.Correct(m)

                        val bpm = it.copy(value = c.get(0, 0).toInt())
                        // Log.v("HeartRateOmeter", "[onBpm] ${it.value} => ${bpm.value}")
                        onBpm(bpm)
                    }*/
                    .subscribeAsState(
                        initial = HeartRateOmeter.Bpm(
                            value = -1,
                            type = HeartRateOmeter.PulseType.OFF
                        )
                    )

                AndroidView(
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(2.dp, Color(0xFF4CB8FF), CircleShape)
                        .align(Alignment.CenterHorizontally),
                    factory = { surfaceView }
                )

                val fingerDetectionText = if (fingerDetected) "Measuring..." else "No finger"

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = fingerDetectionText,
                )

                val heartRateStr = if (heartRate.value < 1)
                    "--"
                else heartRate.value.toString()

                Heart(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    bpm = heartRateStr
                )

            } else {
                val textToShow = if (cameraPermission.status.shouldShowRationale) {
                    // If the user has denied the permission but the rationale can be shown,
                    // then gently explain why the app requires this permission
                    "The camera is important for this app. Please grant the permission."
                } else {
                    // If it's the first time the user lands on this feature, or the user
                    // doesn't want to be asked again for this permission, explain that the
                    // permission is required
                    "Camera permission required for this feature to be available. " +
                            "Please grant the permission"
                }
                Text(textToShow)
                Button(onClick = { cameraPermission.launchPermissionRequest() }) {
                    Text("Request permission")
                }
            }

        }
    }

}

@Composable
private fun rememberSurfaceView(
    widthPx: Int, heightPx: Int,
): SurfaceView {
    val context = LocalContext.current

    return remember {
        SurfaceView(
            context
        ).apply {
            holder.setFixedSize(widthPx, heightPx)
        }
    }
}

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }

@Composable
@Preview
private fun Heart(
    modifier: Modifier = Modifier,
    bpm: String = "--",
) {
    Box(modifier) {
        Image(
            painterResource(R.drawable.heart),
            contentDescription = null,
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally),
                text = bpm,
                style = heartRateTextStyle,
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "bpm",
                style = onboardingDescription,
                color = Color.White,
            )
        }

    }
}
