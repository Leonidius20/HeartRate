package io.github.leonidius20.heartrate.data.measurements

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MeasurementsRepository @Inject constructor() {

    // temporary
    private val measurements = ArrayList<MeasurementEntity>()

    /**
     * @return the id of the measurement in the database.
     */
    fun storeNewMeasurement(bpm: Int): Int {
        measurements.add(
            MeasurementEntity(bpm, System.currentTimeMillis())
        )
        return measurements.size - 1
    }

    fun getAllMeasurements(): List<MeasurementEntity> {
        return measurements
    }

    fun getMeasurementById(id: Int): MeasurementEntity {
        return measurements[id]
    }

}