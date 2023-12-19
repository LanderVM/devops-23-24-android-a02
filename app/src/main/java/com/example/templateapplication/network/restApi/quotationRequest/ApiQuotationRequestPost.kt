package com.example.templateapplication.network.restApi.quotationRequest

import kotlinx.serialization.Serializable

@Serializable
data class ApiQuotationRequestPost (
    val quotationId: Int?,
    val formulaId: Int,
    val eventLocation: Address,
    val startTime: String,
    val endTime: String,
    val equipments: List<EquipmentSelected>,
    val customer: Customer,
    val isTripelBier: Boolean,
    val numberOfPeople: Int,
)

@Serializable
data class Customer(
    val id: Int?,
    val firstName: String,
    val lastName: String,
    val email: Email,
    val billingAddress: Address,
    val phoneNumber: String,
    val vatNumber: String,
)

@Serializable
data class EquipmentSelected(
    val equipmentId: Int,
    val amount: Int,
)

@Serializable
data class Address(
    val street: String = "",
    val houseNumber: String = "",
    val postalCode: String = "",
    val city: String = "",
)

@Serializable
data class Email(
    val email: String,
)

sealed interface ApiQuotationRequestPostApiState {
    object Success : ApiQuotationRequestPostApiState
    data class Error(val errorMessage: String) : ApiQuotationRequestPostApiState
    object Loading : ApiQuotationRequestPostApiState
    object Idle : ApiQuotationRequestPostApiState
}