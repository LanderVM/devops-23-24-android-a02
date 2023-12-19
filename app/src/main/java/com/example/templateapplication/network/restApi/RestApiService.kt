package com.example.templateapplication.network.restApi


import com.example.templateapplication.network.restApi.quotationRequest.ApiGetFormula
import com.example.templateapplication.network.restApi.common.DatesRangeData
import com.example.templateapplication.network.restApi.priceEstimation.ApiGetEstimatedPriceResponse
import com.example.templateapplication.network.restApi.priceEstimation.EstimationDetailsData
import com.example.templateapplication.network.restApi.quotationRequest.ApiQuotationRequestPost
import com.example.templateapplication.network.restApi.quotationRequest.QuotationEquipmentData
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RestApiService {
    @GET("Equipment")
    suspend fun getQuotationEquipment(): QuotationEquipmentData // TODO rename

    @GET("Quotation/Estimation/Details")
    suspend fun getEstimationDetails(): EstimationDetailsData

    @GET("Quotation/Estimation/Calculate")
    suspend fun calculatePrice(
        @Query("formulaId") formulaId: Int,
        @Query("equipmentIds") equipmentIds: List<Int>,
        @Query("startTime") startTime: String,
        @Query("endTime") endTime: String,
        @Query("estimatedNumberOfPeople") estimatedNumberOfPeople: Int,
        @Query("isTripelBier") isTripelBier: Boolean,
    ): ApiGetEstimatedPriceResponse

    @GET("Quotation/Dates")
    suspend fun getDates(): DatesRangeData

    @GET("Formula")
    suspend fun getFormulas(): ApiGetFormula

    @POST("Quotation")
    suspend fun postQuotationRequest(@Body body: ApiQuotationRequestPost): Call<ApiQuotationRequestPost>
}

fun RestApiService.getEquipmentAsFlow() = flow { emit(getQuotationEquipment()) }
fun RestApiService.getFormulasAsFlow() = flow { emit(getFormulas()) }

