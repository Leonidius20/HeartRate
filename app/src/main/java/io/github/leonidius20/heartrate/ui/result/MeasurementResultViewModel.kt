package io.github.leonidius20.heartrate.ui.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.leonidius20.heartrate.data.measurements.MeasurementEntity
import io.github.leonidius20.heartrate.data.measurements.MeasurementsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MeasurementResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repo: MeasurementsRepository,
) : ViewModel() {

    val id = savedStateHandle.get<String>("id")!!.toInt()

    private val _measurement = MutableStateFlow<MeasurementEntity?>(null)
    val measurement = _measurement.asStateFlow()

    init {
        _measurement.value = repo.getMeasurementById(id)
    }

}