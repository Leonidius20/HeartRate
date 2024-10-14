package io.github.leonidius20.heartrate.ui.measure

import android.view.SurfaceView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import net.kibotu.heartrateometer.HeartRateOmeter

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MeasureScreen() {
    Column {
        val surfaceView = rememberSurfaceView()

        val cameraPermission = rememberPermissionState(
            android.Manifest.permission.CAMERA
        )

        if (cameraPermission.status.isGranted) {
            val fingerDetected = remember { mutableStateOf(false) }



            // todo: remember {} this
            val heartRate = HeartRateOmeter()
                .withAverageAfterSeconds(3)
                .setFingerDetectionListener { detected ->

                }
                .bpmUpdates(surfaceView)
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
                    initial = HeartRateOmeter.Bpm(value = -1, type = HeartRateOmeter.PulseType.OFF)
                )

            AndroidView(
                modifier = Modifier.height(100.dp),
                factory = { surfaceView }
            )

            Text(
                text = "${heartRate.value} BPM"
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

@Composable
private fun rememberSurfaceView(): SurfaceView {
    val context = LocalContext.current
    return remember {
        SurfaceView(
           context
        )
    }
}
