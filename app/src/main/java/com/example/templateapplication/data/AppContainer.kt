package com.example.templateapplication.data

import android.content.Context
import androidx.room.Room
import com.example.templateapplication.data.database.BlancheDatabase
import com.example.templateapplication.data.database.QuotationDao
import com.example.templateapplication.network.googleMapsApi.GooglePlacesApiService
import com.example.templateapplication.network.restApi.RestApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


interface AppContainer {
    val googleMapsRepository: GoogleMapsRepository
    val apiRepository: ApiRepository
}

class DefaultAppContainer(
    private val blancheContext: Context
) : AppContainer {
    private val googleMapsBaseUrl = GooglePlacesApiService.BASE_URL
    private val restApiBaseUrl = "https://devops-2324-a02.hogenttiproject.be/api/"

    private val json = Json { ignoreUnknownKeys = true }

    private val logger =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
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

    private val blancheDb: BlancheDatabase by lazy {
        Room.databaseBuilder(blancheContext, BlancheDatabase::class.java, "blanche_db")
            .build()
    }

    private val quotationDao: QuotationDao by lazy {
        blancheDb.quotationDao()
    }

    override val apiRepository: ApiRepository by lazy {
        RestApiRepository(
            quotationDao = quotationDao,
            restApiService = restApiRetrofitService
        )
    }
}
