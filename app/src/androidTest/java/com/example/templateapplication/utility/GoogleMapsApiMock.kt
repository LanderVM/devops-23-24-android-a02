package com.example.templateapplication.utility

import com.example.templateapplication.data.GoogleMapsRepository
import com.example.templateapplication.model.common.googleMaps.GoogleMapsDistance
import com.example.templateapplication.model.common.googleMaps.GoogleMapsPlaceCandidates
import com.example.templateapplication.model.common.googleMaps.GoogleMapsPrediction
import com.example.templateapplication.network.googleMapsApi.GoogleDistanceElement
import com.example.templateapplication.network.googleMapsApi.GoogleDistanceField
import com.example.templateapplication.network.googleMapsApi.GoogleDistanceInKm
import com.example.templateapplication.network.googleMapsApi.GooglePlace
import com.example.templateapplication.network.googleMapsApi.GooglePlaceGeometry
import com.example.templateapplication.network.googleMapsApi.GooglePlaceLatitudes
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
        return GoogleMapsPlaceCandidates(
            listOf(
                GooglePlace(
                    formattedAddress = "Adres",
                    geometry = GooglePlaceGeometry(
                        GooglePlaceLatitudes(
                            lat = 15.0,
                            lng = 20.0,
                        )
                    )
                )
            )
        )
    }

    override suspend fun getDistance(
        departurePlace: String,
        eventPlace: String
    ): GoogleMapsDistance {
        return GoogleMapsDistance(
            listOf(
                GoogleDistanceElement(
                    arrayListOf(
                        GoogleDistanceField(
                            GoogleDistanceInKm(
                                "10km",
                                30,
                            ),
                            "aa"
                        )
                    )
                )
            )
        )
    }
}