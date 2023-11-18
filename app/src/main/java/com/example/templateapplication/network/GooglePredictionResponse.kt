package com.example.templateapplication.network

import kotlinx.serialization.Serializable

@Serializable
data class ApiGooglePredictionResponse(
    val predictions: ArrayList<GooglePrediction>
) {
}

fun ApiGooglePredictionResponse.asDomainObject(): GooglePredictionsResponse {
    return GooglePredictionsResponse(predictions)
}

data class GooglePredictionsResponse(
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