package com.example.templateapplication.network

import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlacesApiService {
    @GET("maps/api/place/autocomplete/json")
    suspend fun getPredictions(
        @Query("key") key: String = "AIzaSyDLmwXCGwUK5jxbqcZD3IqYMvcUcsS_J1c",
        @Query("types") types: String = "address",
        @Query("input") input: String,
        @Query("language") language: String = "nl",
        @Query("region") region: String = "be",
    ): ApiGooglePredictionResponse

    @GET("maps/api/place/findplacefromtext/json")
    suspend fun getPlace(
        @Query("key") key: String = "AIzaSyDLmwXCGwUK5jxbqcZD3IqYMvcUcsS_J1c",
        @Query("types") types: String = "address",
        @Query("input") input: String,
        @Query("language") language: String = "nl",
    ): ApiGooglePlaceResponse

    companion object{
        const val BASE_URL = "https://maps.googleapis.com/"
    }
}
