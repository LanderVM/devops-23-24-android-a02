package com.example.templateapplication.network.guidePriceEstimation

import kotlinx.serialization.Serializable

@Serializable
data class FormulaData(
    val id: Int,
    val title: String,
)

@Serializable
data class EquipmentData(
    val id: Int,
    val title: String,
)

@Serializable
data class EstimationDetails(
    val formulas: List<FormulaData>?,
    val equipment: List<EquipmentData>?,
    val unavailableDays: List<Long>?,
)