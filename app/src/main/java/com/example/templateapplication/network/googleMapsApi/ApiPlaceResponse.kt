package com.example.templateapplication.network.googleMapsApi

import com.example.templateapplication.model.common.googleMaps.GoogleMapsPlace
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
    val formatted_address: String,
    val geometry: GooglePlaceGeometry,
)

fun ApiGooglePlaceResponse.asDomainObject(): GoogleMapsPlace {
    return GoogleMapsPlace(candidates)
}