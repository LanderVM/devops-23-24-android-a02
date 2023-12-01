package com.example.templateapplication.network.googleMapsApi

import com.example.templateapplication.model.common.googleMaps.GoogleMapsPrediction
import kotlinx.serialization.Serializable

@Serializable
data class ApiGooglePredictionResponse(
    val predictions: ArrayList<GooglePrediction>
)

@Serializable
data class GooglePredictionTerm(
    val offset: Int,
    val value: String
)

@Serializable
data class GooglePrediction(
    val description: String,
    val terms: List<GooglePredictionTerm>
)


fun ApiGooglePredictionResponse.asDomainObject(): GoogleMapsPrediction {
    return GoogleMapsPrediction(predictions)
}