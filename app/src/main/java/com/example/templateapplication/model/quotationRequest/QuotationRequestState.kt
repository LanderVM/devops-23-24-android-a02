package com.example.templateapplication.model.quotationRequest

import java.util.Calendar

data class QuotationRequestState(
    val formulaId: Int = -1,
    val startDate: Calendar? = null,
    val endDate: Calendar? = null,
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

