package io.github.leonidius20.heartrate

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.leonidius20.heartrate.ui.measure.MeasureScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    // todo change start dest
    NavHost(navController = navController, startDestination = "measure") {
        composable("measure") {
            MeasureScreen()
        }
    }
}