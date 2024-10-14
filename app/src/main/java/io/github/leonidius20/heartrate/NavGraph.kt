package io.github.leonidius20.heartrate

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.leonidius20.heartrate.ui.measure.MeasureScreen
import io.github.leonidius20.heartrate.ui.onboarding.OnboardingScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    // todo change start dest
    NavHost(navController = navController, startDestination = "onboarding") {
        composable("onboarding") {
            OnboardingScreen()
        }
        composable("measure") {
            MeasureScreen()
        }
    }
}