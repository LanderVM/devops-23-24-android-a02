package com.example.templateapplication.data

import com.example.templateapplication.network.GoogleDistanceResponse
import com.example.templateapplication.network.GooglePlaceResponse
import com.example.templateapplication.network.GooglePlacesApiService
import com.example.templateapplication.network.GooglePredictionsResponse
import com.example.templateapplication.network.asDomainObject
import com.google.android.gms.maps.model.LatLng

interface GoogleMapsRepository {
    suspend fun getPredictions(input: String): GooglePredictionsResponse
    suspend fun getPlace(input: String): GooglePlaceResponse
    suspend fun getDistance(vertrekPlaats: LatLng, eventPlaats: LatLng): GoogleDistanceResponse
}

class ApiGoogleMapsRepository(
    private val googlePlacesApiService: GooglePlacesApiService
): GoogleMapsRepository {
    override suspend fun getPredictions(input: String): GooglePredictionsResponse {
        return googlePlacesApiService.getPredictions(input = input).asDomainObject()
    }

    override suspend fun getPlace(input: String): GooglePlaceResponse {
        return googlePlacesApiService.getPlace(input = input).asDomainObject()
    }

    override suspend fun getDistance(vertrekPlaats: LatLng, eventPlaats: LatLng): GoogleDistanceResponse {
        return googlePlacesApiService.getDistance(origins = "${vertrekPlaats.latitude}, ${vertrekPlaats.longitude}", destinations = "${eventPlaats.latitude}, ${eventPlaats.longitude}").asDomainObject()
    }

}