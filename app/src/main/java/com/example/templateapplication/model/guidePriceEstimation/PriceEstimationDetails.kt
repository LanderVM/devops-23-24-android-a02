package com.example.templateapplication.model.guidePriceEstimation

import com.example.templateapplication.model.common.googleMaps.GoogleMapsPlaceCandidates
import com.example.templateapplication.model.common.googleMaps.GoogleMapsResponse
import com.example.templateapplication.ui.commons.DropDownOption
import java.util.Calendar

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

data class EstimationUiState(
    var dbData: EstimationDetails = EstimationDetails(),
    var selectedFormula: Int = 1,
    var amountOfPeople: Int = 0,
    var wantsTripelBeer: Boolean = false,
    var wantsExtras: Boolean = false,
    var formulaDropDownIsExpanded: Boolean = false,
    val startDate: Calendar? = null,
    val endDate: Calendar? = null,
    val placeResponse: GoogleMapsPlaceCandidates = GoogleMapsPlaceCandidates(),
    val googleMaps: GoogleMapsResponse = GoogleMapsResponse(),
)

sealed interface PriceEstimationDetailsApiState {
    data class Success(val result: EstimationDetails) : PriceEstimationDetailsApiState
    data class Error(val errorMessage: String) : PriceEstimationDetailsApiState
    object Loading : PriceEstimationDetailsApiState
}