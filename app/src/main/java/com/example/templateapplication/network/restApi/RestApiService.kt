package com.example.templateapplication.network.restApi


import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.math.BigDecimal

interface RestApiService {
    @GET("Equipment")
    suspend fun getQuotationEquipment(): QuotationEquipmentData // TODO rename

    @GET("Quotation/Estimation/Details")
    suspend fun getEstimationDetails(): EstimationDetailsData

    @GET("Quotation/Estimation/Calculate")
    suspend fun calculatePrice(): BigDecimal

    @GET("Quotation/Dates")
    suspend fun getDates(): DatesRangeData
    @GET("Formula")
    suspend fun getFormulas(): ApiGetFormula
    @POST("Quotation")
    suspend fun postQuotationRequest(@Body body: ApiQuotationRequestPost): Call<ApiQuotationRequestPost>
}

fun RestApiService.getEquipmentAsFlow() = flow { emit(getQuotationEquipment())}
fun RestApiService.getFormulasAsFlow() = flow { emit(getFormulas())}

