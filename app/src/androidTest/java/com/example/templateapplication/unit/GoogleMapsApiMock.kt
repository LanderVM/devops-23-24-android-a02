package com.example.templateapplication.unit

import com.example.templateapplication.data.GoogleMapsRepository
import com.example.templateapplication.model.common.googleMaps.GoogleMapsDistance
import com.example.templateapplication.model.common.googleMaps.GoogleMapsPlaceCandidates
import com.example.templateapplication.model.common.googleMaps.GoogleMapsPrediction
import com.example.templateapplication.network.googleMapsApi.GooglePrediction
import com.example.templateapplication.network.googleMapsApi.GooglePredictionTerm

class GoogleMapsApiMock : GoogleMapsRepository {
    override suspend fun getPredictions(input: String): GoogleMapsPrediction =
        GoogleMapsPrediction(
            listOf(
                GooglePrediction(
                    "ADescription",
                    listOf(GooglePredictionTerm(10, "AA"), GooglePredictionTerm(20, "AB"))
                ),
                GooglePrediction(
                    "BDescription",
                    listOf(GooglePredictionTerm(30, "BA"), GooglePredictionTerm(40, "BB"))
                ),
            )
        )

    override suspend fun getPlace(input: String): GoogleMapsPlaceCandidates {
        TODO("Not yet implemented")
    }

    override suspend fun getDistance(
        vertrekPlaats: String,
        eventPlaats: String
    ): GoogleMapsDistance {
        TODO("Not yet implemented")
    }
}