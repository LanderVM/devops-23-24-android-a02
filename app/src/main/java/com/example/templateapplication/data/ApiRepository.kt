package com.example.templateapplication.data


import android.util.Log
import com.example.templateapplication.data.database.QuotationDao
import com.example.templateapplication.data.database.asDomainObjects
import com.example.templateapplication.model.common.quotation.Equipment
import com.example.templateapplication.model.common.quotation.Formula
import com.example.templateapplication.model.guidePriceEstimation.EstimationDetails
import com.example.templateapplication.model.quotationRequest.DisabledDatesState
import com.example.templateapplication.model.quotationRequest.ExtraItemState
import com.example.templateapplication.network.restApi.RestApiService
import com.example.templateapplication.network.restApi.common.asDomainObjects
import com.example.templateapplication.network.restApi.getEquipmentAsFlow
import com.example.templateapplication.network.restApi.getFormulasAsFlow
import com.example.templateapplication.network.restApi.priceEstimation.ApiGetEstimatedPriceResponse
import com.example.templateapplication.network.restApi.priceEstimation.asDomainObject
import com.example.templateapplication.network.restApi.quotationRequest.ApiQuotationEquipment
import com.example.templateapplication.network.restApi.quotationRequest.ApiQuotationRequestPost
import com.example.templateapplication.network.restApi.quotationRequest.FormulaData
import com.example.templateapplication.network.restApi.quotationRequest.asDbEquipment
import com.example.templateapplication.network.restApi.quotationRequest.asDbFormula
import com.example.templateapplication.network.restApi.quotationRequest.asDomainObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import retrofit2.Call

interface ApiRepository {
    suspend fun insertEquipment(item: ApiQuotationEquipment)
    fun getEquipment(): Flow<List<Equipment>>
    suspend fun insertFormula(item: FormulaData)
    fun getFormulas(): Flow<List<Formula>>
    suspend fun getUnavailableDateRanges(): List<DisabledDatesState>
    suspend fun refresh()

    suspend fun getQuotationExtraEquipment(): List<ExtraItemState>

    suspend fun getEstimationDetails(): EstimationDetails
    suspend fun calculatePrice(
        formulaId: Int,
        equipmentIds: List<Int>,
        startTime: String,
        endTime: String,
        estimatedNumberOfPeople: Int,
        isTripelBier: Boolean
    ): ApiGetEstimatedPriceResponse

    suspend fun postQuotationRequest(body: ApiQuotationRequestPost): Call<ApiQuotationRequestPost>
}

class RestApiRepository(
    private val restApiService: RestApiService,
    private val quotationDao: QuotationDao
) : ApiRepository {

    override suspend fun insertEquipment(item: ApiQuotationEquipment) {
        Log.i("ApiRepository insertEquipment", "Starting insert into RoomDb: $item")
        val dbEquipment = item.asDbEquipment()
        Log.i("ApiRepository insertEquipment", "Converted to databaase item: $dbEquipment")
        quotationDao.insertEquipment(item.asDbEquipment())
        Log.i("ApiRepository insertEquipment", "Inserted into RoomDb.")
    }

    override fun getEquipment(): Flow<List<Equipment>> =
        quotationDao.getEquipment()
            .map {
                it.asDomainObjects()
            }.onEach { if (it.isEmpty()) refresh() }

    override suspend fun insertFormula(item: FormulaData) {
        Log.i("ApiRepository insertFormula", "Starting insert into RoomDb: $item")
        val dbFormula = item.asDbFormula()
        Log.i("ApiRepository insertFormula", "Converted to databaase item: $dbFormula")
        quotationDao.insertFormula(item.asDbFormula())
        Log.i("ApiRepository insertFormula", "Inserted into RoomDb.")
    }

    override fun getFormulas(): Flow<List<Formula>> =
        quotationDao.getFormulas()
            .map {
                it.asDomainObjects()
            }.onEach { if (it.isEmpty()) refresh() }

    override suspend fun refresh() {
        restApiService.getEquipmentAsFlow().collect {
            Log.i("RestApi getEquipment", "Retrieving latest equipment list from api..")
            for (equipment in it.equipment) {
                Log.i("RestApi getEquipment", "Inserting into RoomDb: $it")
                insertEquipment(equipment)
            }
            Log.i("RestApi getEquipment", "Retrieved latest equipment from api.")
        }
        restApiService.getFormulasAsFlow().collect {
            Log.i("RestApi getFormulas", "Retrieving latest formula list from api..")
            for (formula in it.formulas) {
                Log.i("RestApi getFormulas", "Inserting into RoomDb: $it")
                insertFormula(formula)
            }
            Log.i("RestApi getEquipment", "Retrieved latest equipment from api.")
        }
    }

    override suspend fun getQuotationExtraEquipment() =
        restApiService.getQuotationEquipment().asDomainObjects()

    override suspend fun getEstimationDetails() =
        restApiService.getEstimationDetails().asDomainObject()

    override suspend fun calculatePrice(
        formulaId: Int,
        equipmentIds: List<Int>,
        startTime: String,
        endTime: String,
        estimatedNumberOfPeople: Int,
        isTripelBier: Boolean
    ) = restApiService.calculatePrice(
        formulaId,
        equipmentIds,
        startTime,
        endTime,
        estimatedNumberOfPeople,
        isTripelBier,
    )

    override suspend fun getUnavailableDateRanges(): List<DisabledDatesState> {
        Log.i("RestAPI getDateRanges", "Retrieving list of date ranges from api..")
        return restApiService.getDates().asDomainObjects()
    }

    override suspend fun postQuotationRequest(body: ApiQuotationRequestPost): Call<ApiQuotationRequestPost> {
        Log.i("RestAPI postQuotationRequest", "Attempting to POST a new quotation request to api..")
        return restApiService.postQuotationRequest(body)
    }

}