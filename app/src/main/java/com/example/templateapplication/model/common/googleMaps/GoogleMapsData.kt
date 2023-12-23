package com.example.templateapplication.model.common.googleMaps

import com.example.templateapplication.network.googleMapsApi.GoogleDistanceElement
import com.example.templateapplication.network.googleMapsApi.GooglePlace
import com.example.templateapplication.network.googleMapsApi.GooglePrediction
import com.google.android.gms.maps.model.LatLng

data class GoogleMapsDistance(
    val rows: List<GoogleDistanceElement> = emptyList()
)

data class GoogleMapsPlaceCandidates(
    val candidates: List<GooglePlace> = emptyList()
)

data class GoogleMapsPrediction(
    val predictions: List<GooglePrediction> = emptyList()
)

data class GoogleMapsResponse(
    val predictionsResponse: GoogleMapsPrediction = GoogleMapsPrediction(),
    val eventAddressAutocompleteCandidates: GoogleMapsPlaceCandidates = GoogleMapsPlaceCandidates(),
    val predictions: List<GooglePrediction> = emptyList(),
    val eventAddress: String = "",
    val marker: LatLng = LatLng(50.93735122680664, 4.03336238861084),
    val distanceResponse: GoogleMapsDistance = GoogleMapsDistance(),
)