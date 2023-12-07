package com.example.templateapplication.model.quotationRequest

import android.util.Log
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

fun parseAddress(addressString: String): Address { // TODO move to api stuff
    Log.i("RestApi parseAddress", "Address to parse: $addressString")
    val parts = addressString.split(",")
    Log.i("RestApi parseAddress", "Address parts: $parts")
    val streetHouseNumber = parts[0].trim().split(" ")
    Log.i("RestApi parseAddress", "assisting object streetHouseNumber: $streetHouseNumber")
    val street = streetHouseNumber.dropLast(1).joinToString(" ")
    Log.i("RestApi parseAddress", "Parsed street: $street")
    val houseNumber = streetHouseNumber.last()
    Log.i("RestApi parseAddress", "Parsed houseNumber: $houseNumber")

    val postalCodeCity = parts[1].trim().split(" ")
    Log.i("RestApi parseAddress", "assisting object postalCodeCity: $postalCodeCity")
    val postalCode = postalCodeCity[0]
    Log.i("RestApi parseAddress", "Parsed postalCode: $postalCode")
    val city = postalCodeCity.subList(1, postalCodeCity.size).joinToString(" ")
    Log.i("RestApi parseAddress", "Parsed city: $city")

    return Address(street, houseNumber, postalCode, city)
}