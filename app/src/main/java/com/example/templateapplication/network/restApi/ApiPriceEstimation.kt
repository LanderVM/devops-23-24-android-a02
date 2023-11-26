package com.example.templateapplication.network.restApi

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
data class EstimationUnavailableDateRanges(
    val startTime: Long,
    val endTime: Long,
)

@Serializable
data class EstimationDetailsData(
    val formulas: List<EstimationFormulaData>?,
    val equipment: List<EstimationEquipmentData>?,
    val unavailableDates: List<EstimationUnavailableDateRanges>?,
)