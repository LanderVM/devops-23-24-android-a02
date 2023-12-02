package com.example.templateapplication.model.quotationRequest

import com.example.templateapplication.model.common.googleMaps.GoogleMapsPlaceCandidates
import java.util.Calendar

data class QuotationRequestState(
    val startDate: Calendar? = null,
    val endDate: Calendar? = null,
    val selectedFormula: Int = -1,
    val placeResponse: GoogleMapsPlaceCandidates = GoogleMapsPlaceCandidates(),
    val amountOfPeople: String = "",
    val wantsTripelBeer: Boolean = false,
)
