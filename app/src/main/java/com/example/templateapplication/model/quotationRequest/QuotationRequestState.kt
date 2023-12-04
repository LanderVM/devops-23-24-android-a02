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
    val customer: Customer = Customer(),
    val addedItems: List<ExtraItemState> = emptyList(),
) {
    data class Customer(
        val firstName: String = "",
        val lastName: String = "",
        val phoneNumber: String = "",
        val email: String = "",
        val vatNumber: String = "",
        val billingAddress: Address = Address(),
    )
}

data class Address(
    val street: String = "",
    val houseNumber: String = "",
    val postalCode: String = "",
    val city: String = "",
)