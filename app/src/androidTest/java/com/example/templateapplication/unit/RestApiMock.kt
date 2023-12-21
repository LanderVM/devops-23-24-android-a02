package com.example.templateapplication.unit

import com.example.templateapplication.data.ApiRepository
import com.example.templateapplication.model.common.quotation.DisabledDateRange
import com.example.templateapplication.model.common.quotation.Equipment
import com.example.templateapplication.model.common.quotation.Formula
import com.example.templateapplication.model.guidePriceEstimation.EstimationDetails
import com.example.templateapplication.model.guidePriceEstimation.EstimationEquipment
import com.example.templateapplication.model.quotationRequest.ExtraItemState
import com.example.templateapplication.network.restApi.about.ApiEmailPost
import com.example.templateapplication.network.restApi.about.ApiEmailResponse
import com.example.templateapplication.network.restApi.priceEstimation.ApiGetEstimatedPriceResponse
import com.example.templateapplication.network.restApi.quotationRequest.ApiQuotationEquipment
import com.example.templateapplication.network.restApi.quotationRequest.ApiQuotationRequestPost
import com.example.templateapplication.network.restApi.quotationRequest.FormulaData
import com.example.templateapplication.ui.commons.DropDownOption
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import java.time.Instant

class RestApiMock : ApiRepository {

    private val oneDay: Long = 86400

    override suspend fun insertEquipment(item: ApiQuotationEquipment) {
        TODO("Not yet implemented")
    }

    override fun getEquipment(): Flow<List<Equipment>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertFormula(item: FormulaData) {
        TODO("Not yet implemented")
    }

    override fun getFormulas(): Flow<List<Formula>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUnavailableDateRanges(): List<DisabledDateRange> {
        TODO("Not yet implemented")
    }

    override suspend fun refresh() {
        TODO("Not yet implemented")
    }

    override suspend fun getQuotationExtraEquipment(): List<ExtraItemState> {
        TODO("Not yet implemented")
    }

    override suspend fun getEstimationDetails(): EstimationDetails =
        EstimationDetails(
            formulas = listOf(DropDownOption("A", 1), DropDownOption("B", 2)),
            equipment = listOf(
                EstimationEquipment(1, "E.A"),
                EstimationEquipment(2, "E.B"),
                EstimationEquipment(3, "E.C")
            ),
            unavailableDates = listOf(
                DisabledDateRange(
                    Instant.now(),
                    Instant.now().plusSeconds(oneDay)
                )
            )
        )

    override suspend fun calculatePrice(
        formulaId: Int,
        equipmentIds: List<Int>,
        startTime: String,
        endTime: String,
        estimatedNumberOfPeople: Int,
        isTripelBier: Boolean
    ): ApiGetEstimatedPriceResponse {
        TODO("Not yet implemented")
    }

    override suspend fun postQuotationRequest(body: ApiQuotationRequestPost): Call<ApiQuotationRequestPost> {
        TODO("Not yet implemented")
    }

    override suspend fun postEmail(body: ApiEmailPost): ApiEmailResponse {
        TODO("Not yet implemented")
    }
}