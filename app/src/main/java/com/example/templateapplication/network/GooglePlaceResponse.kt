package com.example.templateapplication.network

import kotlinx.serialization.Serializable

@Serializable
data class ApiGooglePlaceResponse(
    val candidates: ArrayList<GooglePlace>
) {
}

fun ApiGooglePlaceResponse.asDomainObject(): GooglePlaceResponse {
    return GooglePlaceResponse(candidates)
}

data class GooglePlaceResponse(
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