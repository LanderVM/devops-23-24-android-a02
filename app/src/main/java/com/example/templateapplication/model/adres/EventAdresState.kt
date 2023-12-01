package com.example.templateapplication.model.adres

import com.example.templateapplication.model.common.googleMaps.GoogleMapsDistance
import com.example.templateapplication.model.common.googleMaps.GoogleMapsPlace
import com.example.templateapplication.model.common.googleMaps.GoogleMapsPrediction
import com.google.android.gms.maps.model.LatLng

data class GoogleMapsPredictionsState(
    val predictionsResponse: GoogleMapsPrediction,
    val input: String = ""
)

data class GoogleMapsPlaceState(
    val placeResponse: GoogleMapsPlace,
    val marker: LatLng = LatLng(50.93735122680664, 4.03336238861084),
)

data class GoogleMapsDistanceState(
    val distanceResponse: GoogleMapsDistance
)

sealed interface ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>
    object Error : ApiResponse<Nothing>
    object Loading : ApiResponse<Nothing>
}