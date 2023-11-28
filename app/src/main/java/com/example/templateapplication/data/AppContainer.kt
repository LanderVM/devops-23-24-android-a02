package com.example.templateapplication.data

import com.example.templateapplication.network.GooglePlacesApiService
import com.example.templateapplication.network.restApi.RestApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


interface AppContainer {
    val googleMapsRepository: GoogleMapsRepository
    val apiRepository: ApiRepository
}

class DefaultAppContainer: AppContainer {
    private val googleMapsBaseUrl = GooglePlacesApiService.BASE_URL
    private val restApiBaseUrl = "http://10.0.2.2:5292/api/" // TODO move & quick test

    private val json = Json { ignoreUnknownKeys = true }

    private val logger = HttpLoggingInterceptor().apply{level = HttpLoggingInterceptor.Level.BODY}
    private val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

    private val googleMapsRetrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(googleMapsBaseUrl)
        .client(client)
        .build()

    private val retrofitApi = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(restApiBaseUrl)
        .client(client)
        .build()

    private val googleMapsRetrofitService: GooglePlacesApiService by lazy {
        googleMapsRetrofit.create(GooglePlacesApiService::class.java)
    }

    private val restApiRetrofitService: RestApiService by lazy {
        retrofitApi.create(RestApiService::class.java)
    }
    override val googleMapsRepository: ApiGoogleMapsRepository by lazy {
        ApiGoogleMapsRepository(googleMapsRetrofitService)
    }

    override val apiRepository: ApiRepository by lazy {
        RestApiRepository(restApiRetrofitService)
    }
}
