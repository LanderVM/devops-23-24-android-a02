package com.example.templateapplication.model.quotationRequest

import com.example.templateapplication.model.common.googleMaps.GoogleMapsResponse
import com.example.templateapplication.ui.commons.DropDownOption

data class QuotationUiState(
    val dropDownExpanded: Boolean = false,
    val hasError: Boolean = false,
    val dateError: String? = null, // TODO
    val eventAddressError: String = "",
    val beerDropDownOptions: List<DropDownOption> = listOf(
        DropDownOption("Pils", 0),
        DropDownOption("Tripel", 1),
    ),
    val googleMaps: GoogleMapsResponse = GoogleMapsResponse(),
)

