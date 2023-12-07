package com.example.templateapplication.network.restApi;

import android.util.Log
import com.example.templateapplication.model.guidePriceEstimation.EstimationDetails
import com.example.templateapplication.model.quotationRequest.DisabledDatesState
import com.example.templateapplication.ui.commons.DropDownOption
import kotlinx.serialization.Serializable;

@Serializable
data class DatesRangeData(
        val disabledDatesRange: List<DisabledDatesData> = emptyList(),
)

@Serializable
data class DisabledDatesData(
        val startTime: String,
        val endTime: String
)

fun DatesRangeData.asDomainObjects(): List<DisabledDatesState> {
         var domainList = this.disabledDatesRange.map {
                DisabledDatesState(it.startTime, it.endTime)
        }
        return domainList

}