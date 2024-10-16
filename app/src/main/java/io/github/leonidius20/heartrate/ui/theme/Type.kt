package io.github.leonidius20.heartrate.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import io.github.leonidius20.heartrate.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val rubik = FontFamily(
    Font(R.font.rubik_regular, FontWeight.Normal),
    Font(R.font.rubik_semi_bold, FontWeight.SemiBold),
    Font(R.font.rubik_bold, FontWeight.Bold), // W700
    Font(R.font.rubik_medium, FontWeight.Medium), // W500
)

val onboardingTitle = TextStyle(
    fontFamily = rubik,
    fontWeight = FontWeight.SemiBold,
    fontSize = 28.sp,
    textAlign = TextAlign.Center,
)

val onboardingDescription = TextStyle(
    fontFamily = rubik,
    fontWeight = FontWeight.Normal,
    fontSize = 22.sp,
    textAlign = TextAlign.Center,
)

val heartRateTextStyle = TextStyle(
    fontFamily = rubik,
    fontWeight = FontWeight.Bold,
    fontSize = 62.sp,
    textAlign = TextAlign.Center,
    color = Color.White,
)

val appNameTextStyle = TextStyle(
    fontFamily = rubik,
    fontWeight = FontWeight.Bold,
    fontSize = 51.sp,
    textAlign = TextAlign.Center,
    color = Color.Black,
)

val resultTitleTextStyle = TextStyle(
    fontFamily = rubik,
    fontWeight = FontWeight.Medium,
    fontSize = 22.sp,
    color = Color.Black,
)

val resultTypeTextStyle = TextStyle(
    fontFamily = rubik,
    fontWeight = FontWeight.Bold,
    fontSize = 28.sp,
)