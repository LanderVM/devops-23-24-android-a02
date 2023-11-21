package com.example.templateapplication.network.extraMateriaal

import com.example.templateapplication.model.extraMateriaal.ExtraItemState
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET



//create the actual function implementations (expensive!)
//no longer needed --> moved to the AppContainer
//object TaskApi{
//
//}


//define what the API looks like
interface ExtraMateriaalApiService {
    //suspend is added to force the user to call this in a coroutine scope
    @GET("equipment")
    suspend fun getExtraMateriaal(): EquipmentData
}


