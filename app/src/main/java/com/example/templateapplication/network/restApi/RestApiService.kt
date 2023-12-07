package com.example.templateapplication.network.restApi


import retrofit2.http.GET
import java.math.BigDecimal

interface RestApiService {
    @GET("Equipment")
    suspend fun getQuotationEquipment(): QuotationEquipmentData

    @GET("Quotation/Estimation/Details")
    suspend fun getEstimationDetails(): EstimationDetailsData

    @GET("Quotation/Estimation/Calculate")
    suspend fun calculatePrice(): BigDecimal

    @GET("Quotation/Dates")
    suspend fun getDates(): DatesRangeData
}


