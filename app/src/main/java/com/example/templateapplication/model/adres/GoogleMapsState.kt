package com.example.templateapplication.network

import com.google.android.gms.maps.model.LatLng

data class GoogleMapsPredictionsState(
    val predictionsResponse: GooglePredictionsResponse,
    val input: String = ""
)

data class GoogleMapsPlaceState(
    val placeResponse: GooglePlaceResponse,
    val marker: LatLng = LatLng(50.93735122680664, 4.03336238861084),
)

data class GoogleMapsDistanceState(
    val distanceResponse: GoogleDistanceResponse
)

sealed interface ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>
    object Error : ApiResponse<Nothing>
    object Loading : ApiResponse<Nothing>
}