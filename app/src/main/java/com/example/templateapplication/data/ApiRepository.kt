package com.example.templateapplication.data


import com.example.templateapplication.model.extraMateriaal.ExtraItemState
import com.example.templateapplication.model.guidePriceEstimation.PriceEstimationDetailsState
import com.example.templateapplication.network.restApi.RestApiService
import com.example.templateapplication.network.restApi.asDomainObjects
import java.math.BigDecimal

interface ApiRepository {
    suspend fun getQuotationExtraEquipment(): List<ExtraItemState>
    suspend fun getEstimationDetails(): PriceEstimationDetailsState
    suspend fun calculatePrice(): BigDecimal
}

class RestApiRepository(
    private val restApiService: RestApiService
): ApiRepository{
    override suspend fun getQuotationExtraEquipment(): List<ExtraItemState> {
        return restApiService.getQuotationEquipment().asDomainObjects()
    }

    override suspend fun getEstimationDetails(): PriceEstimationDetailsState {
        return restApiService.getEstimationDetails()
    }

    override suspend fun calculatePrice(): BigDecimal {
        return restApiService.calculatePrice()
    }

}