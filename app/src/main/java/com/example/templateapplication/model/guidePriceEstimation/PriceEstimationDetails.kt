package com.example.templateapplication.model.guidePriceEstimation

data class EstimationFormula(
    val id: Int,
    val title: String,
)

data class EstimationEquipment(
    val id: Int,
    val title: String,
)

data class EstimationUnavailableDateRanges(
    val startTime: Long,
    val endTime: Long,
)

data class EstimationDetails(
    val formulas: List<EstimationFormula>?,
    val equipment: List<EstimationEquipment>?,
    val unavailableDates: List<EstimationUnavailableDateRanges>?,
)

sealed interface PriceEstimationDetailsApiState{
    data class Success(val result: EstimationDetails) : PriceEstimationDetailsApiState
    data class Error(val errorMessage: String): PriceEstimationDetailsApiState
    object Loading : PriceEstimationDetailsApiState
}