package io.github.leonidius20.heartrate.ui.measure

sealed interface MeasureUiState {

    data object NoFingerDetected : MeasureUiState

    data class Measuring(
        /**
         * from 0.0 to 1.0
         */
        val progress: Float,
    ) : MeasureUiState

    data class Completed(
        val measurementId: Int,
    ) : MeasureUiState

}