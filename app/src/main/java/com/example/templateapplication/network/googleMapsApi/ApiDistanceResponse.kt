package com.example.templateapplication.network.googleMapsApi

import com.example.templateapplication.model.common.googleMaps.GoogleMapsDistance
import kotlinx.serialization.Serializable

@Serializable
data class ApiGoogleDistanceResponse(
    val rows: ArrayList<GoogleDistanceElement>
)

@Serializable
data class GoogleDistanceInKm(
    val text: String,
    val value: Long
)

@Serializable
data class GoogleDistanceField(
    val distance: GoogleDistanceInKm,
    val status: String
)

@Serializable
data class GoogleDistanceElement(
    val elements: ArrayList<GoogleDistanceField>,
)

fun ApiGoogleDistanceResponse.asDomainObject(): GoogleMapsDistance {
    return GoogleMapsDistance(rows)
}

