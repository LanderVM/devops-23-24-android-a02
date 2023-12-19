package com.example.templateapplication.network.restApi.priceEstimation

import com.example.templateapplication.model.guidePriceEstimation.EstimationDetails
import com.example.templateapplication.model.guidePriceEstimation.EstimationEquipment
import com.example.templateapplication.model.guidePriceEstimation.EstimationUnavailableDateRanges
import com.example.templateapplication.ui.commons.DropDownOption
import kotlinx.serialization.Serializable

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
    val unavailableDates: List<EstimationUnavailableDateRangesData> = emptyList(),
)

fun EstimationDetailsData.asDomainObject(): EstimationDetails {
    val formulas = this.formulas.map {
        DropDownOption(it.title, it.id)
    }
    val equipment = this.equipment.map {
        EstimationEquipment(it.id, it.title)
    }
    val dateRanges = this.unavailableDates.map {
        EstimationUnavailableDateRanges(it.startTime, it.endTime)
    }
    return EstimationDetails(formulas, equipment, dateRanges)
}