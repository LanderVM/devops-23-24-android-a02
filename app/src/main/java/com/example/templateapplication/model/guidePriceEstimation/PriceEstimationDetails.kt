package com.example.templateapplication.model.guidePriceEstimation

import com.example.templateapplication.network.restApi.EstimationEquipmentData
import com.example.templateapplication.network.restApi.EstimationUnavailableDateRanges
import com.example.templateapplication.network.restApi.EstimationFormulaData

data class PriceEstimationDetailsState(
    val formulas: List<EstimationFormulaData>,
    val equipment: List<EstimationEquipmentData>,
    val unavailableDates: List<EstimationUnavailableDateRanges>,
)

sealed interface PriceEstimationDetailsApiState{
    data class Success(val result: PriceEstimationDetailsState) : PriceEstimationDetailsApiState
    data class Error(val errorMessage: String): PriceEstimationDetailsApiState
    object Loading : PriceEstimationDetailsApiState
}