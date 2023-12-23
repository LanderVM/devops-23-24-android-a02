package com.example.templateapplication.network.restApi.common

import android.util.Log
import com.example.templateapplication.model.common.quotation.DisabledDateRange
import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.format.DateTimeFormatter

@Serializable
data class DatesRangeData(
    val dateRanges: List<DisabledDatesData>,
)

@Serializable
data class DisabledDatesData(
    val startTime: String,
    val endTime: String,
)

fun DatesRangeData.asDomainObjects(): List<DisabledDateRange> {
    Log.i("RestAPI getDateRanges", "Converting data to list of DisabledDatesState..")
    val domainList = this.dateRanges.map {
        DisabledDateRange(
            DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX")
                .parse(it.startTime, Instant::from),
            DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX").parse(it.endTime, Instant::from)
        )
    }

    domainList.forEach {
        Log.i(
            "RestAPI getDateRanges",
            "Item converted to DisabledDatesState: from ${it.startTime} to ${it.endTime}"
        )
    }
    return domainList
}