package com.example.templateapplication.model.quotationRequest

import com.example.templateapplication.ui.commons.DropDownOption
import java.util.Calendar

data class QuotationRequestState(
    val startDate: Calendar? = null,
    val endDate: Calendar? = null,
    val eventAddress: String = "",
    val selectedFormula: Int = -1,
    val amountOfPeople: String = "",
    val wantsTripelBeer: Boolean = false,
    val beerDropDownOptions: List<DropDownOption> = listOf(
        DropDownOption("Pils", 0),
        DropDownOption("Tripel", 1),
    ),
)
