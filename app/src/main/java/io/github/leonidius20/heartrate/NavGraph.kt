package io.github.leonidius20.heartrate

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.leonidius20.heartrate.ui.homepage.HomeScreen
import io.github.leonidius20.heartrate.ui.measure.MeasureScreen
import io.github.leonidius20.heartrate.ui.onboarding.OnboardingScreen
import io.github.leonidius20.heartrate.ui.result.MeasurementResultScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    // todo change start dest if user went through onboarding already
    NavHost(navController = navController, startDestination = "onboarding") {
        composable("onboarding") {
            OnboardingScreen(
                onNavigateToMeasurement = {
                    navController.navigate("homepage")
                })
        }
        composable("homepage") {
            HomeScreen(
                onNavigateToMeasurement = {
                    navController.navigate("measure")
                }
            )
        }
        composable("measure") {
            MeasureScreen(
                backToHomePage = {
                    navController.popBackStack()
                },
                goToResultScreen = { measurementId ->
                    navController.navigate("result/${measurementId}")
                },
            )
        }
        composable("result/{id}") {
            MeasurementResultScreen(
                backToHomepage = {
                    navController.popBackStack("homepage", inclusive = false)
                }
            )
        }
    }
}