package io.github.leonidius20.heartrate.ui.measure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.leonidius20.heartrate.data.measurements.MeasurementsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeasureViewModel @Inject constructor(
    private val measurementsRepository: MeasurementsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MeasureUiState>(
        MeasureUiState.NoFingerDetected
    )
    val uiState = _uiState.asStateFlow()

    private var measuringTimer : Job? = null

    fun onFingerDetectionChange(fingerDetected: Boolean) {
        if (uiState.value is MeasureUiState.NoFingerDetected && fingerDetected) {
            startMeasuring()
        } else if (uiState.value is MeasureUiState.Measuring && !fingerDetected) {
            abortMeasuring()
        }
    }

    val recordedBpmValues = mutableListOf<Int>()

    private fun startMeasuring() {
        _uiState.value = MeasureUiState.Measuring(progress = 0f)
        measuringTimer = viewModelScope.launch {
            // a total of 5sec for measuring
            repeat(5) { iteration ->
                delay(1_000)
                _uiState.value = MeasureUiState.Measuring(progress = iteration / 4f)
            }

            val averageBpm = recordedBpmValues.average().toInt()
            val id = measurementsRepository.storeNewMeasurement(averageBpm)
            _uiState.value = MeasureUiState.Completed(measurementId = id)
        }
    }

    private fun abortMeasuring() {
        measuringTimer?.cancel()
        _uiState.value = MeasureUiState.NoFingerDetected
    }

    fun onBpmValueRecorded(value: Int) {
        if (uiState.value is MeasureUiState.Measuring) {
            recordedBpmValues.add(value)
        }
    }

}