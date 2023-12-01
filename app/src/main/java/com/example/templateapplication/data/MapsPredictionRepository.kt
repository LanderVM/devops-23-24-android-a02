package com.example.templateapplication.data

import com.example.templateapplication.model.common.googleMaps.GoogleMapsDistance
import com.example.templateapplication.model.common.googleMaps.GoogleMapsPlace
import com.example.templateapplication.model.common.googleMaps.GoogleMapsPrediction
import com.example.templateapplication.network.googleMapsApi.GooglePlacesApiService
import com.example.templateapplication.network.googleMapsApi.asDomainObject

interface GoogleMapsRepository {
    suspend fun getPredictions(input: String): GoogleMapsPrediction
    suspend fun getPlace(input: String): GoogleMapsPlace
    suspend fun getDistance(vertrekPlaats: String, eventPlaats: String): GoogleMapsDistance
}

class ApiGoogleMapsRepository(
    private val googlePlacesApiService: GooglePlacesApiService
): GoogleMapsRepository {
    override suspend fun getPredictions(input: String): GoogleMapsPrediction {
        return googlePlacesApiService.getPredictions(input = input).asDomainObject()
    }

    override suspend fun getPlace(input: String): GoogleMapsPlace {
        return googlePlacesApiService.getPlace(input = input).asDomainObject()
    }

    override suspend fun getDistance(vertrekPlaats: String, eventPlaats: String): GoogleMapsDistance {
        return googlePlacesApiService.getDistance(origins = vertrekPlaats, destinations = eventPlaats).asDomainObject()
    }

}