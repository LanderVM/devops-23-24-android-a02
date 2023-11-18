package com.example.templateapplication.data

import com.example.templateapplication.network.GooglePlacesApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface GoogleMapsAppContainer {
    val googleMapsRepository: GoogleMapsRepository
}

class DefaultGoogleMapsAppContainerAppContainer(): GoogleMapsAppContainer {
    private val baseUrl = GooglePlacesApiService.BASE_URL

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: GooglePlacesApiService by lazy {
        retrofit.create(GooglePlacesApiService::class.java)
    }

    override val googleMapsRepository: ApiGoogleMapsRepository by lazy {
        ApiGoogleMapsRepository(retrofitService)
    }
}
