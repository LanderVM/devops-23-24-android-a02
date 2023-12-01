package com.example.templateapplication.model.common.googleMaps

import com.example.templateapplication.network.googleMapsApi.GoogleDistanceElement
import com.example.templateapplication.network.googleMapsApi.GooglePlace
import com.example.templateapplication.network.googleMapsApi.GooglePrediction

data class GoogleMapsDistance(
    val rows: ArrayList<GoogleDistanceElement>
)

data class GoogleMapsPlace(
    val candidates: ArrayList<GooglePlace>
)

data class GoogleMapsPrediction(
    val predictions: ArrayList<GooglePrediction>
)