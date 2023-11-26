package com.example.templateapplication.data

import com.example.templateapplication.network.GooglePlacesApiService
import com.example.templateapplication.network.extraMateriaal.ExtraMateriaalApiService
import com.example.templateapplication.network.guidePriceEstimation.GuidePriceEstimationApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


interface AppContainer {
    val googleMapsRepository: GoogleMapsRepository
    val extraMateriaalRepository: ExtraMateriaalRepository
    val guidePriceEstimationRepository: GuidePriceEstimationRepository
}

class DefaultAppContainer(): AppContainer {
    private val googleMapsBaseUrl = GooglePlacesApiService.BASE_URL
    private val restApiBaseUrl = "http://10.0.2.2:5292/api/"

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

    private val extraMateriaalRetrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(restApiBaseUrl)
        .client(client)
        .build()

    private val guidePriceEstimationRetrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(restApiBaseUrl)
        .client(client)
        .build()

    private val googleMapsRetrofitService: GooglePlacesApiService by lazy {
        googleMapsRetrofit.create(GooglePlacesApiService::class.java)
    }

    private val extraMateriaalRetrofitService: ExtraMateriaalApiService by lazy {
        extraMateriaalRetrofit.create(ExtraMateriaalApiService::class.java)
    }

    private val guidePriceEstimationRetrofitService: GuidePriceEstimationApiService by lazy {
        guidePriceEstimationRetrofit.create(GuidePriceEstimationApiService::class.java)
    }

    override val googleMapsRepository: ApiGoogleMapsRepository by lazy {
        ApiGoogleMapsRepository(googleMapsRetrofitService)
    }

    override val extraMateriaalRepository: ExtraMateriaalRepository by lazy {
        ApiExtraMateriaalRepository(extraMateriaalRetrofitService)
    }

    override val guidePriceEstimationRepository: GuidePriceEstimationRepository by lazy {
        ApiGuidePriceEstimationRepository(guidePriceEstimationRetrofitService)
    }
}
