package com.example.templateapplication.network.googleMapsApi

import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Properties

interface GooglePlacesApiService {
    @GET("maps/api/place/autocomplete/json")
    suspend fun getPredictions(
        @Query("key") key: String = "AIzaSyDLmwXCGwUK5jxbqcZD3IqYMvcUcsS_J1c",
        @Query("types") types: String = "address",
        @Query("input") input: String,
        @Query("language") language: String = "nl",
        @Query("components") components: String = "country:BE",
        ): ApiGooglePredictionResponse

    @GET("maps/api/place/findplacefromtext/json")
    suspend fun getPlace(
        @Query("key") key: String = "AIzaSyDLmwXCGwUK5jxbqcZD3IqYMvcUcsS_J1c",
        @Query("inputtype") types: String = "textquery",
        @Query("input") input: String,
        @Query("language") language: String = "nl",
        @Query("fields") fields: String = "formatted_address,geometry",
    ): ApiGooglePlaceResponse

    @GET("maps/api/distancematrix/json")
    suspend fun getDistance(
        @Query("key") key: String = "AIzaSyDLmwXCGwUK5jxbqcZD3IqYMvcUcsS_J1c",
        @Query("destinations") destinations: String,
        @Query("origins") origins: String,
    ): ApiGoogleDistanceResponse


    companion object{
        const val BASE_URL = "https://maps.googleapis.com/"
    }
}
