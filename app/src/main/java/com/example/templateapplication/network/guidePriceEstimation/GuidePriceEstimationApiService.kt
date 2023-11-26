package com.example.templateapplication.network.guidePriceEstimation

import com.example.templateapplication.model.guidePriceEstimation.PriceEstimationDetailsState
import retrofit2.http.GET
import java.math.BigDecimal

interface GuidePriceEstimationApiService {

    @GET("PriceEstimation/Details")
    suspend fun getEstimationDetails(): PriceEstimationDetailsState

    @GET("PriceEstimation/Calculate")
    suspend fun calculatePrice(): BigDecimal
}