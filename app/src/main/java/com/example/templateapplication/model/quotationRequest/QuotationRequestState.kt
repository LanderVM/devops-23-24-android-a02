package com.example.templateapplication.model.quotationRequest

import com.example.templateapplication.model.common.googleMaps.GoogleMapsPlace
import com.example.templateapplication.ui.commons.DropDownOption
import java.util.Calendar

data class QuotationRequestState(
    val startDate: Calendar? = null,
    val endDate: Calendar? = null,
    val selectedFormula: Int = -1,
    val placeResponse: GoogleMapsPlace = GoogleMapsPlace(), // TODO check: this or eventAddress?
    val amountOfPeople: String = "",
    val wantsTripelBeer: Boolean = false,
    val beerDropDownOptions: List<DropDownOption> = listOf(
        DropDownOption("Pils", 0),
        DropDownOption("Tripel", 1),
    ),
)
