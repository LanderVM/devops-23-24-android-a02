package com.example.templateapplication.data


import android.util.Log
import com.example.templateapplication.model.guidePriceEstimation.EstimationDetails
import com.example.templateapplication.model.quotationRequest.DisabledDatesState
import com.example.templateapplication.model.quotationRequest.ExtraItemState
import com.example.templateapplication.network.restApi.RestApiService
import com.example.templateapplication.network.restApi.asDomainObject
import com.example.templateapplication.network.restApi.asDomainObjects
import java.math.BigDecimal

interface ApiRepository {
    suspend fun getQuotationExtraEquipment(): List<ExtraItemState>
    suspend fun getEstimationDetails(): EstimationDetails
    suspend fun calculatePrice(): BigDecimal
    suspend fun getDateRanges(): List<DisabledDatesState>
}

class RestApiRepository(
    private val restApiService: RestApiService
) : ApiRepository {
    override suspend fun getQuotationExtraEquipment() =
        restApiService.getQuotationEquipment().asDomainObjects()

    override suspend fun getEstimationDetails() =
        restApiService.getEstimationDetails().asDomainObject()

    override suspend fun calculatePrice() = restApiService.calculatePrice()
    override suspend fun getDateRanges(): List<DisabledDatesState> {
        Log.i("RestAPI getDateRanges", "Retrieving list of date ranges from api..")
        return restApiService.getDates().asDomainObjects()
    }

}