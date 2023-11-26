package com.example.templateapplication.model.guidePriceEstimation

import com.example.templateapplication.network.guidePriceEstimation.EquipmentData
import com.example.templateapplication.network.guidePriceEstimation.FormulaData

data class PriceEstimationDetailsState(
    val formulas: List<FormulaData>,
    val equipment: List<EquipmentData>,
    val unavailableDays: List<Long>,
)

sealed interface PriceEstimationDetailsApiState{
    data class Success(val priceEstimationDetails: PriceEstimationDetailsState) : PriceEstimationDetailsApiState
    data class Error(val errorMessage: String): PriceEstimationDetailsApiState
    object Loading : PriceEstimationDetailsApiState
}