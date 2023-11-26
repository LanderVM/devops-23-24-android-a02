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
data class EstimationDetailsData(
    val formulas: List<EstimationFormulaData>?,
    val equipment: List<EstimationEquipmentData>?,
    val unavailableDays: List<Long>?,
)