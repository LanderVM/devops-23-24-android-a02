package com.example.templateapplication.network.restApi


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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
    @POST("Quotation")
    suspend fun postQuotationRequest(@Body body: ApiQuotationRequestPost): Call<ApiQuotationRequestPost>
}


