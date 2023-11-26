package com.example.templateapplication.network.extraMateriaal

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


