package com.example.templateapplication.network.googleMapsApi

import com.example.templateapplication.model.common.googleMaps.GoogleMapsPlaceCandidates
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiGooglePlaceResponse(
    val candidates: ArrayList<GooglePlace>
)

@Serializable
data class GooglePlaceLatitudes(
    val lat: Double,
    val lng: Double,
)

@Serializable
data class GooglePlaceGeometry(
    val location: GooglePlaceLatitudes,
)

@Serializable
data class GooglePlace(
    @SerialName("formatted_address")
    val formattedAddress: String,
    val geometry: GooglePlaceGeometry,
)

fun ApiGooglePlaceResponse.asDomainObject(): GoogleMapsPlaceCandidates {
    return GoogleMapsPlaceCandidates(candidates)
}