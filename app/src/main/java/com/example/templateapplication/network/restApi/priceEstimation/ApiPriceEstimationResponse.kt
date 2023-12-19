package com.example.templateapplication.network.restApi.priceEstimation

import com.example.templateapplication.model.common.quotation.DisabledDateRange
import com.example.templateapplication.model.guidePriceEstimation.EstimationDetails
import com.example.templateapplication.model.guidePriceEstimation.EstimationEquipment
import com.example.templateapplication.network.restApi.common.DisabledDatesData
import com.example.templateapplication.ui.commons.DropDownOption
import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.format.DateTimeFormatter

@Serializable
data class EstimationFormulaData(
    val id: Int,
    val title: String,
)

@Serializable
data class EstimationEquipmentData(
    val id: Int,
    val title: String,
)

@Serializable
data class EstimationUnavailableDateRangesData(
    val startTime: Long,
    val endTime: Long,
)

@Serializable
data class EstimationDetailsData(
    val formulas: List<EstimationFormulaData> = emptyList(),
    val equipment: List<EstimationEquipmentData> = emptyList(),
    val unavailableDates: List<DisabledDatesData> = emptyList(),
)

fun EstimationDetailsData.asDomainObject(): EstimationDetails {
    val formulas = this.formulas.map {
        DropDownOption(it.title, it.id)
    }
    val equipment = this.equipment.map {
        EstimationEquipment(it.id, it.title)
    }
    val dateRanges = this.unavailableDates.map {
        DisabledDateRange(
            DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX").parse(it.startTime, Instant::from),
            DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX").parse(it.endTime, Instant::from)
        )
    }
    return EstimationDetails(formulas, equipment, dateRanges)
}