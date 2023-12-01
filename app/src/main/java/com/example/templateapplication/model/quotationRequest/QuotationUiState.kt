package com.example.templateapplication.model.quotationRequest

import com.example.templateapplication.model.common.googleMaps.GoogleMapsDistance
import com.example.templateapplication.model.common.googleMaps.GoogleMapsPrediction
import com.example.templateapplication.network.googleMapsApi.GooglePrediction
import com.google.android.gms.maps.model.LatLng

data class QuotationUiState(
    val dropDownExpanded: Boolean = false,
    val hasError: Boolean = false,
    val dateError: String? = null, // TODO
    val eventAddressError: String = "",
    val googleMaps: GoogleMapsResponse = GoogleMapsResponse()

) {
    data class GoogleMapsResponse(
        val predictionsResponse: GoogleMapsPrediction = GoogleMapsPrediction(), // TODO rename
        val predictions: List<GooglePrediction> = emptyList(),
        val eventAddress: String = "",
        val marker: LatLng = LatLng(50.93735122680664, 4.03336238861084),
        val distanceResponse: GoogleMapsDistance = GoogleMapsDistance(),
    )
}