package com.example.templateapplication.data

import com.example.templateapplication.model.common.googleMaps.GoogleMapsDistance
import com.example.templateapplication.model.common.googleMaps.GoogleMapsPlaceCandidates
import com.example.templateapplication.model.common.googleMaps.GoogleMapsPrediction
import com.example.templateapplication.network.googleMapsApi.GooglePlacesApiService
import com.example.templateapplication.network.googleMapsApi.asDomainObject

interface GoogleMapsRepository {
    suspend fun getPredictions(input: String): GoogleMapsPrediction
    suspend fun getPlace(input: String): GoogleMapsPlaceCandidates
    suspend fun getDistance(departurePlace: String, eventPlace: String): GoogleMapsDistance
}

class ApiGoogleMapsRepository(
    private val googlePlacesApiService: GooglePlacesApiService
) : GoogleMapsRepository {
    override suspend fun getPredictions(input: String): GoogleMapsPrediction {
        return googlePlacesApiService.getPredictions(input = input).asDomainObject()
    }

    override suspend fun getPlace(input: String): GoogleMapsPlaceCandidates {
        return googlePlacesApiService.getPlace(input = input).asDomainObject()
    }

    override suspend fun getDistance(
        departurePlace: String,
        eventPlace: String
    ): GoogleMapsDistance {
        return googlePlacesApiService.getDistance(
            origins = departurePlace,
            destinations = eventPlace
        ).asDomainObject()
    }

}