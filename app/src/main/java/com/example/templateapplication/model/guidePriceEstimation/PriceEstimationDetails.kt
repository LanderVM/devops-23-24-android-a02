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
    val formulas: List<EstimationFormula> = emptyList(),
    val equipment: List<EstimationEquipment> = emptyList(),
    val unavailableDates: List<EstimationUnavailableDateRanges> = emptyList(),
)

data class EstimationScreenState(
    var dbDetails: EstimationDetails = EstimationDetails(),
    var selectedFormula: Int = 1,
)

sealed interface PriceEstimationDetailsApiState{
    data class Success(val result: EstimationDetails) : PriceEstimationDetailsApiState
    data class Error(val errorMessage: String): PriceEstimationDetailsApiState
    object Loading : PriceEstimationDetailsApiState
}