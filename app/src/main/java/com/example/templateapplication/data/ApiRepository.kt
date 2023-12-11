package com.example.templateapplication.data


import android.util.Log
import com.example.templateapplication.data.database.QuotationDao
import com.example.templateapplication.data.database.asDomainObjects
import com.example.templateapplication.model.common.quotation.Equipment
import com.example.templateapplication.model.guidePriceEstimation.EstimationDetails
import com.example.templateapplication.model.quotationRequest.DisabledDatesState
import com.example.templateapplication.model.quotationRequest.ExtraItemState
import com.example.templateapplication.network.restApi.ApiQuotationEquipment
import com.example.templateapplication.network.restApi.ApiQuotationRequestPost
import com.example.templateapplication.network.restApi.RestApiService
import com.example.templateapplication.network.restApi.asDbEquipment
import com.example.templateapplication.network.restApi.asDomainObject
import com.example.templateapplication.network.restApi.asDomainObjects
import com.example.templateapplication.network.restApi.getEquipmentAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import retrofit2.Call
import java.math.BigDecimal

interface ApiRepository {
    suspend fun insertEquipment(item: ApiQuotationEquipment)
    fun getEquipment(): Flow<List<Equipment>>
    suspend fun getUnavailableDateRanges(): List<DisabledDatesState>
    suspend fun refresh()

    suspend fun getQuotationExtraEquipment(): List<ExtraItemState>

    suspend fun getEstimationDetails(): EstimationDetails
    suspend fun calculatePrice(): BigDecimal
    suspend fun postQuotationRequest(body: ApiQuotationRequestPost): Call<ApiQuotationRequestPost>
}

class RestApiRepository(
    private val restApiService: RestApiService,
    private val quotationDao: QuotationDao
) : ApiRepository {

    override suspend fun insertEquipment(item: ApiQuotationEquipment) {
        Log.i("ApiRepository insertEquipment", "Starting insert into RoomDb: $item")
        var dbEquipment = item.asDbEquipment()
        Log.i("ApiRepository insertEquipment", "Converted to databaase item: $dbEquipment")
        quotationDao.insertEquipment(item.asDbEquipment())
        Log.i("ApiRepository insertEquipment", "Inserted into RoomDb.")
    }

    override fun getEquipment(): Flow<List<Equipment>> =
        quotationDao.getEquipment()
            .map {
            it.asDomainObjects()
        }.onEach { if (it.isEmpty()) refresh() }

    override suspend fun refresh() {
        restApiService.getEquipmentAsFlow().collect {
            Log.i("RestApi getEquipment", "Retrieving latest equipment list from api..")
            Log.i("RestApi getEquipment", "Found: $it")
            for (equipment in it.equipment) {
                Log.i("RestApi getEquipment", "Inserting into RoomDb: $it")
                insertEquipment(equipment)
            }
            Log.i("RestApi getEquipment", "Retrieved latest equipment from api.")
        }
    }

    override suspend fun getQuotationExtraEquipment() =
        restApiService.getQuotationEquipment().asDomainObjects()

    override suspend fun getEstimationDetails() =
        restApiService.getEstimationDetails().asDomainObject()

    override suspend fun calculatePrice() = restApiService.calculatePrice()
    override suspend fun getUnavailableDateRanges(): List<DisabledDatesState> {
        Log.i("RestAPI getDateRanges", "Retrieving list of date ranges from api..")
        return restApiService.getDates().asDomainObjects()
    }
    override suspend fun postQuotationRequest(body: ApiQuotationRequestPost): Call<ApiQuotationRequestPost> {
        Log.i("RestAPI postQuotationRequest", "Attempting to POST a new quotation request to api..")
        return restApiService.postQuotationRequest(body)
    }

}