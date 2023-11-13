package com.example.templateapplication.network

import kotlinx.serialization.Serializable

@Serializable
data class ApiGooglePlaceResponse(
    val place: ArrayList<GooglePlace>
) {
}

fun ApiGooglePlaceResponse.asDomainObject(): GooglePlaceResponse {
    return GooglePlaceResponse(place)
}

data class GooglePlaceResponse(
    val places: ArrayList<GooglePlace>
)


@Serializable
data class GooglePlaceLatitudes(
    val lat: Int,
    val lng: Int
)
@Serializable
data class GooglePlaceGeometry(
    val location: GooglePlaceLatitudes,
)
@Serializable
data class GooglePlace(
    val geometry: GooglePlaceGeometry,
    val address: String,
)