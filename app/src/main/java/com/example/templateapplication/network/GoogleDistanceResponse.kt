package com.example.templateapplication.network

import kotlinx.serialization.Serializable

@Serializable
data class ApiGoogleDistanceResponse(
    val rows: ArrayList<GoogleDistanceElement>
) {
}

fun ApiGoogleDistanceResponse.asDomainObject(): GoogleDistanceResponse {
    return GoogleDistanceResponse(rows)
}

data class GoogleDistanceResponse(
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