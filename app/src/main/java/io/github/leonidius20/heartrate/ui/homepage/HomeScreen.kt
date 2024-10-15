package io.github.leonidius20.heartrate.ui.homepage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.leonidius20.heartrate.R
import io.github.leonidius20.heartrate.ui.common.BoxWithCircleBackground
import io.github.leonidius20.heartrate.ui.theme.topBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Preview(widthDp = 393, heightDp = 852)
@Composable
fun HomeScreen(
    onNavigateToMeasurement: () -> Unit = {},
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
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topBarColor,
                )
            )
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            BoxWithCircleBackground(
                Modifier.weight(1f)
            ) {
                Column(Modifier.fillMaxSize()) {
                    Text("Make your first measurement", style = MaterialTheme.typography.titleLarge)
                }
            }

                Image(

                    painter = painterResource(R.drawable.heart_rate_button),
                    contentDescription = "start measuring",
                    contentScale = ContentScale.FillWidth,
                    // todo: fix btn not quite centered
                    modifier = Modifier
                        //.size(134.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            onNavigateToMeasurement()
                        }
                    ,
                )

        }
    }
}