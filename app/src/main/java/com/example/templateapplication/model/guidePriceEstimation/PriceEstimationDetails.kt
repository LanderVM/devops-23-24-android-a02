package com.example.templateapplication.model.guidePriceEstimation

import com.example.templateapplication.ui.commons.DropDownOption

data class EstimationEquipment(
    val id: Int,
    val title: String,
)

data class EstimationUnavailableDateRanges(
    val startTime: Long,
    val endTime: Long,
)

data class EstimationDetails(
    val formulas: List<DropDownOption> = emptyList(),
    val equipment: List<EstimationEquipment> = emptyList(),
    val unavailableDates: List<EstimationUnavailableDateRanges> = emptyList(),
)

data class EstimationScreenState(
    var dbData: EstimationDetails = EstimationDetails(),
    var selectedFormula: Int = 1,
    var amountOfPeople: String = "",
    var wantsTripelBeer: Boolean = false,
    var wantsExtras: Boolean = false,
    var formulaDropDownIsExpanded: Boolean = false,
)

sealed interface PriceEstimationDetailsApiState{
    data class Success(val result: EstimationDetails) : PriceEstimationDetailsApiState
    data class Error(val errorMessage: String): PriceEstimationDetailsApiState
    object Loading : PriceEstimationDetailsApiState
}