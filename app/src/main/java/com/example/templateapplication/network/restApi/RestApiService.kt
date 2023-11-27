package com.example.templateapplication.network.restApi

import retrofit2.http.GET
import java.math.BigDecimal

interface RestApiService {
    @GET("equipment")
    suspend fun getQuotationEquipment(): QuotationEquipmentData

    @GET("PriceEstimation/Details")
    suspend fun getEstimationDetails(): EstimationDetailsData

    @GET("PriceEstimation/Calculate")
    suspend fun calculatePrice(): BigDecimal
}


