package com.example.templateapplication.validation

import com.example.templateapplication.model.UiText

/**
 * Represents events in the main UI that trigger state changes.
 *
 * This sealed class defines various events related to user input changes, such as address,
 * number of people, personal details, etc. Each event carries the respective data that has changed.
 */
sealed class MainEvent {
    data class AddressChanged(val address: String) : MainEvent()
    data class NumberOfPeopleChanged(val numberOfPeople: String) : MainEvent()
    data class FirstNameChanged(val firstName: String) : MainEvent()
    data class LastNameChanged(val lastName: String) : MainEvent()
    data class EmailChanged(val email: String) : MainEvent()
    data class PhoneNumberChanged(val phoneNumber: String) : MainEvent()
    data class StreetChanged(val street: String) : MainEvent()
    data class HouseNumberChanged(val houseNumber: String) : MainEvent()
    data class CityChanged(val city: String) : MainEvent()
    data class PostalCodeChanged(val postalCode: String) : MainEvent()
    data class VatChanged(val vat: String) : MainEvent()
}

/**
 * Represents the state of the main UI.
 *
 * Holds the current state of various user inputs along with potential error messages represented
 * by [UiText]. Includes functions to check readiness for different stages like quotation or
 * personal details submission.
 */
data class MainState(
    val address: String = "",
    val addressError: UiText? = null,
    val numberOfPeople: String = "",
    val numberOfPeopleError: UiText? = null,
    val firstName: String = "",
    val firstNameError: UiText? = null,
    val lastName: String = "",
    val lastNameError: UiText? = null,
    val phoneNumber: String = "",
    val phoneNumberError: UiText? = null,
    val email: String = "",
    val emailError: UiText? = null,
    val street: String = "",
    val streetError: UiText? = null,
    val houseNumber: String = "",
    val houseNumberError: UiText? = null,
    val city: String = "",
    val cityError: UiText? = null,
    val postalCode: String = "",
    val postalCodeError: UiText? = null,
    val vat: String = "",
    val vatError: UiText? = null,
) {
    fun isReadyForQuotation(): Boolean {
        return firstName.isNotEmpty() && firstNameError == null &&
                lastName.isNotEmpty() && lastNameError == null &&
                phoneNumber.isNotEmpty() && phoneNumberError == null &&
                email.isNotEmpty() && emailError == null &&
                street.isNotEmpty() && streetError == null &&
                houseNumber.isNotEmpty() && houseNumberError == null &&
                city.isNotEmpty() && cityError == null &&
                postalCode.isNotEmpty() && postalCodeError == null &&
                vatError == null
    }

    fun isReadyForPersonalDetails(): Boolean {
        return numberOfPeople.isNotEmpty() && numberOfPeopleError == null &&
                address.isNotEmpty() && addressError == null
    }
}