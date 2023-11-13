package com.example.templateapplication.data

import com.example.templateapplication.network.GooglePlaceResponse
import com.example.templateapplication.network.GooglePlacesApiService
import com.example.templateapplication.network.GooglePredictionsResponse
import com.example.templateapplication.network.asDomainObject

interface GoogleMapsRepository {
    suspend fun getPredictions(input: String): GooglePredictionsResponse
    suspend fun getPlace(input: String): GooglePlaceResponse

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

}