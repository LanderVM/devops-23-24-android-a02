package com.example.templateapplication.network.restApi.priceEstimation

import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class ApiGetEstimatedPriceResponse(
    val estimatedPrice: Double,
)

sealed interface PriceEstimationResultApiState {
    data class Success(val result: BigDecimal) : PriceEstimationResultApiState
    data class Error(val errorMessage: String) : PriceEstimationResultApiState
    object Loading : PriceEstimationResultApiState
    object Idle : PriceEstimationResultApiState
}