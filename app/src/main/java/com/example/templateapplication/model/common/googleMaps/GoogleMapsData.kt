package com.example.templateapplication.model.common.googleMaps

import com.example.templateapplication.network.googleMapsApi.GoogleDistanceElement
import com.example.templateapplication.network.googleMapsApi.GooglePlace
import com.example.templateapplication.network.googleMapsApi.GooglePrediction

data class GoogleMapsDistance(
    val rows: List<GoogleDistanceElement> = emptyList()
)

data class GoogleMapsPlace(
    val candidates: List<GooglePlace> = emptyList()
)

data class GoogleMapsPrediction(
    val predictions: List<GooglePrediction> = emptyList()
)