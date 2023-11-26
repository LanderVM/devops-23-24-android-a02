package com.example.templateapplication.data

import com.example.templateapplication.model.guidePriceEstimation.PriceEstimationDetailsState
import com.example.templateapplication.network.guidePriceEstimation.GuidePriceEstimationApiService
import java.math.BigDecimal

interface GuidePriceEstimationRepository {
    suspend fun getEstimationDetails(): PriceEstimationDetailsState
    suspend fun calculatePrice(): BigDecimal
}

class ApiGuidePriceEstimationRepository(
    private val guidePriceEstimationApiService: GuidePriceEstimationApiService
) : GuidePriceEstimationRepository {
    override suspend fun getEstimationDetails(): PriceEstimationDetailsState {
        return guidePriceEstimationApiService.getEstimationDetails()
    }

    override suspend fun calculatePrice(): BigDecimal {
        return guidePriceEstimationApiService.calculatePrice()
    }

}