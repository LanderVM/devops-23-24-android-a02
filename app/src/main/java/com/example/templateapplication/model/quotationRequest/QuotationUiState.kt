package com.example.templateapplication.model.quotationRequest

import com.example.templateapplication.model.common.googleMaps.GoogleMapsDistance
import com.example.templateapplication.model.common.googleMaps.GoogleMapsPrediction
import com.example.templateapplication.network.googleMapsApi.GooglePrediction
import com.example.templateapplication.ui.commons.DropDownOption
import com.google.android.gms.maps.model.LatLng

data class QuotationUiState(
    val dropDownExpanded: Boolean = false,
    val hasError: Boolean = false,
    val dateError: String? = null, // TODO
    val eventAddressError: String = "",
    val beerDropDownOptions: List<DropDownOption> = listOf(
        DropDownOption("Pils", 0),
        DropDownOption("Tripel", 1),
    ),
    val googleMaps: GoogleMapsResponse = GoogleMapsResponse(),
)
data class GoogleMapsResponse( // TODO move to common
    val predictionsResponse: GoogleMapsPrediction = GoogleMapsPrediction(), // TODO rename
    val predictions: List<GooglePrediction> = emptyList(),
    val eventAddress: String = "",
    val marker: LatLng = LatLng(50.93735122680664, 4.03336238861084),
    val distanceResponse: GoogleMapsDistance = GoogleMapsDistance(),
)
