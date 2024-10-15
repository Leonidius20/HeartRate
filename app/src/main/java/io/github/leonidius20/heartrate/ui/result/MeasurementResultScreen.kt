package io.github.leonidius20.heartrate.ui.result

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.leonidius20.heartrate.data.measurements.MeasurementEntity
import io.github.leonidius20.heartrate.ui.common.BoxWithCircleBackground
import io.github.leonidius20.heartrate.ui.theme.btnColor
import io.github.leonidius20.heartrate.ui.theme.topBarColor

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
        modifier = Modifier.fillMaxSize(),
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
                .padding(innerPadding)
        ) {
            BoxWithCircleBackground(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                if (measurement != null) {
                    Text("this is a result of ${measurement.bpm} BPM")
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