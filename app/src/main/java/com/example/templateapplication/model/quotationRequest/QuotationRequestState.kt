package com.example.templateapplication.model.quotationRequest

import java.util.Calendar

data class QuotationRequestState(
    val formulaId: Int = -1,
    val eventLocation: Address = Address(),
    val startTime: Calendar? = null,
    val endTime: Calendar? = null,
    val equipments: List<ExtraItemState> = emptyList(),
    val customer: Customer = Customer(),
    val isTripelBier: Boolean = false,
    val numberOfPeople: Int = 0,
) {
    data class Customer(
        val firstName: String = "",
        val lastName: String = "",
        val email: String = "",
        val billingAddress: Address = Address(),
        val phoneNumber: String = "",
        val vatNumber: String = "",
    )
}

data class Address(
    val street: String = "",
    val houseNumber: String = "",
    val postalCode: String = "",
    val city: String = "",
)

fun parseAddress(addressString: String): Address {
    val parts = addressString.split(",")

    val streetHouseNumber = parts[0].trim().split(" ")
    val street = streetHouseNumber.dropLast(1).joinToString(" ")
    val houseNumber = streetHouseNumber.last()

    val postalCode = parts[1].trim()
    val city = parts[2].trim()

    return Address(street, houseNumber, postalCode, city)
}