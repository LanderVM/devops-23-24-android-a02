package com.example.templateapplication.ui.screens.quotationRequest

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.templateapplication.api.RestApiApplication
import com.example.templateapplication.data.ApiRepository
import com.example.templateapplication.data.GoogleMapsRepository
import com.example.templateapplication.model.UiText
import com.example.templateapplication.model.adres.ApiResponse
import com.example.templateapplication.model.common.googleMaps.GoogleMapsResponse
import com.example.templateapplication.model.extraMateriaal.ExtraItemDetailsApiState
import com.example.templateapplication.model.quotationRequest.DateRangesApiState
import com.example.templateapplication.model.quotationRequest.ExtraItemState
import com.example.templateapplication.model.quotationRequest.QuotationRequestState
import com.example.templateapplication.model.quotationRequest.QuotationUiState
import com.example.templateapplication.validation.ValidateEmailUseCase
import com.example.templateapplication.validation.ValidateNotEmptyUseCase
import com.example.templateapplication.validation.ValidatePhoneNumberUseCase
import com.example.templateapplication.validation.ValidateVatUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class QuotationRequestViewModel(
    private val restApiRepository: ApiRepository,
    private val googleMapsRepository: GoogleMapsRepository
) :
    ViewModel() {

    init {
        getApiExtraEquipment()
        getDateRanges()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RestApiApplication)
                val googleMapsRepository = application.container.googleMapsRepository
                val guidePriceEstimationRepository =
                    application.container.apiRepository
                QuotationRequestViewModel(
                    restApiRepository = guidePriceEstimationRepository,
                    googleMapsRepository = googleMapsRepository
                )
            }
        }
    }

    fun canNavigateNext(): Boolean {
        return true //TODO fix
    }

    // ---------------------------------------- EVENT DETAILS: EVENT DETAILS
    private val _quotationRequestState = MutableStateFlow(QuotationRequestState())
    val quotationRequestState = _quotationRequestState.asStateFlow()

    private val _quotationUiState = MutableStateFlow(QuotationUiState())
    val quotationUiState = _quotationUiState.asStateFlow()

    var dateRangesApiState: DateRangesApiState by mutableStateOf(DateRangesApiState.Loading)
        private set

    fun updateDateRange(beginDate: Long?, endDate: Long?) {
        val begin = Calendar.getInstance()
        val end = Calendar.getInstance()

        if (beginDate != null) {
            begin.timeInMillis = beginDate
            if (endDate != null) end.timeInMillis = endDate else end.timeInMillis = beginDate
        }

        _quotationRequestState.update {
            it.copy(startTime = begin, endTime = end)
        }
    }


    fun getDateRange(): String {
        if (_quotationRequestState.value.startTime == null || _quotationRequestState.value.endTime == null) {
            return "/"
        }
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)

        return "${dateFormat.format(_quotationRequestState.value.startTime!!.timeInMillis)} - " +
                dateFormat.format(_quotationRequestState.value.endTime!!.timeInMillis)
    }

    //// New getDateRanges
    fun getDateRanges() {
        viewModelScope.launch {
            try {
                val listDatesResult = restApiRepository.getDateRanges()
                _quotationUiState.update {
                    it.copy(listDateRanges = listDatesResult)
                }
                dateRangesApiState = DateRangesApiState.Success(listDatesResult)
            } catch (e: IOException) {
                val errorMessage = e.message ?: "An error occurred"
                Log.e(
                    "RestApi getDateRanges",
                    e.message ?: "Failed to retrieve date ranges from api"
                )
                dateRangesApiState = DateRangesApiState.Error(errorMessage)
            }

        }
    }


    // TODO validate each field separately. Only check if hasErrors = true here.

    fun selectFormula(id: Int) {
        _quotationRequestState.update {
            it.copy(formulaId = id)
        }
    }

    fun selectBeer(wantsTripelBeer: Int) {
        _quotationRequestState.update {
            it.copy(isTripelBier = wantsTripelBeer == 1)
        }
    }

    fun setDropDownExpanded(value: Boolean) {
        _quotationUiState.update {
            it.copy(dropDownExpanded = value)
        }
    }

    fun setAmountOfPeople(amountOfPeople: String) {
        _quotationRequestState.update {
            it.copy(numberOfPeople = amountOfPeople.toInt())
        }
    }

    // ---------------------------------------- EVENT DETAILS: GOOGLE MAPS

    var googleMapsApiState: ApiResponse<GoogleMapsResponse> by mutableStateOf(
        ApiResponse.Loading
    )
        private set

    fun updateInput(input: String) {
        _quotationUiState.update {
            it.copy(googleMaps = it.googleMaps.copy(eventAddress = input))
        }
    }

    fun getPredictions() {
        viewModelScope.launch {
            try {
                val listResult =
                    googleMapsRepository.getPredictions(input = _quotationUiState.value.googleMaps.eventAddress)
                _quotationUiState.update {
                    it.copy(googleMaps = it.googleMaps.copy(predictionsResponse = listResult))
                }
                googleMapsApiState = ApiResponse.Success(
                    GoogleMapsResponse(predictionsResponse = listResult)
                )
            } catch (e: IOException) {
                googleMapsApiState = ApiResponse.Error
                Log.i("Error", e.toString())
            }
        }
    }

    private fun updateDistance() {
        if (placeFound()) {
            viewModelScope.launch {
                try {
                    val distanceResult = googleMapsRepository.getDistance(
                        vertrekPlaats = "${_quotationUiState.value.googleMaps.marker.latitude}, ${_quotationUiState.value.googleMaps.marker.longitude}",
                        eventPlaats = _quotationUiState.value.googleMaps.eventAddressAutocompleteCandidates.candidates[0].formatted_address
                    )
                    _quotationUiState.update {
                        it.copy(googleMaps = it.googleMaps.copy(distanceResponse = distanceResult))
                    }
                    googleMapsApiState =
                        ApiResponse.Success(GoogleMapsResponse(distanceResponse = distanceResult))
                } catch (e: IOException) {
                    googleMapsApiState = ApiResponse.Error
                    Log.i("Error", e.toString())
                }
            }
        }
    }

    fun updateMarker() {
        viewModelScope.launch {
            try {
                val placeResult =
                    googleMapsRepository.getPlace(input = _quotationUiState.value.googleMaps.eventAddress)
                _quotationUiState.update {
                    it.copy(
                        googleMaps = it.googleMaps.copy(eventAddressAutocompleteCandidates = placeResult)
                    )
                }
                googleMapsApiState = ApiResponse.Success(
                    GoogleMapsResponse(eventAddress = placeResult.toString())
                )
                updateDistance()
            } catch (e: IOException) {
                googleMapsApiState = ApiResponse.Error
                Log.i("Error", e.toString())
            }
        }
    }

    fun getDistanceLong(): Long? {
        if (_quotationUiState.value.googleMaps.distanceResponse.rows.isEmpty() or _quotationUiState.value.googleMaps.eventAddress.isBlank()) {
            return null
        }
        return _quotationUiState.value.googleMaps.distanceResponse.rows[0].elements[0].distance.value
    }

    fun getDistanceString(): String {
        if (_quotationUiState.value.googleMaps.distanceResponse.rows.isEmpty() or _quotationUiState.value.googleMaps.eventAddress.isBlank()) {
            return ""
        }
        return "Afstand: " + _quotationUiState.value.googleMaps.distanceResponse.rows[0].elements[0].distance.text
    }

    fun placeFound(): Boolean {
        return if (_quotationUiState.value.googleMaps.eventAddressAutocompleteCandidates.candidates.isNotEmpty())
            _quotationUiState.value.googleMaps.eventAddressAutocompleteCandidates.candidates[0].formatted_address.isNotEmpty()
        else false
    }

    // ----------------------------------------  PERSONAL DETAILS

    fun setFirstName(firstName: String) {
        _quotationRequestState.update {
            it.copy(
                customer = it.customer.copy(
                    firstName = firstName
                )
            )
        }
    }

    fun setLastName(lastName: String) {
        _quotationRequestState.update {
            it.copy(
                customer = it.customer.copy(
                    lastName = lastName
                )
            )
        }
    }

    fun setPhoneNumber(phoneNumber: String) {
        _quotationRequestState.update {
            it.copy(
                customer = it.customer.copy(
                    phoneNumber = phoneNumber
                )
            )
        }
    }

    fun setEmail(email: String) {
        _quotationRequestState.update {
            it.copy(
                customer = it.customer.copy(
                    email = email
                )
            )
        }
    }

    fun setStreet(street: String) {
        _quotationRequestState.update {
            it.copy(
                customer = it.customer.copy(
                    billingAddress = it.customer.billingAddress.copy(
                        street = street
                    )
                )
            )
        }
    }

    fun setHouseNumber(houseNumber: String) {
        _quotationRequestState.update {
            it.copy(
                customer = it.customer.copy(
                    billingAddress = it.customer.billingAddress.copy(
                        houseNumber = houseNumber
                    )
                )
            )
        }
    }

    fun setCity(city: String) {
        _quotationRequestState.update {
            it.copy(
                customer = it.customer.copy(
                    billingAddress = it.customer.billingAddress.copy(
                        city = city
                    )
                )
            )
        }
    }

    fun setPostalCode(postalCode: String) {
        _quotationRequestState.update {
            it.copy(
                customer = it.customer.copy(
                    billingAddress = it.customer.billingAddress.copy(
                        postalCode = postalCode
                    )
                )
            )
        }
    }

    fun setVatNumber(vatNumber: String) {
        _quotationRequestState.update {
            it.copy(
                customer = it.customer.copy(
                    vatNumber = vatNumber
                )
            )
        }
    }

// ---------------------------------------- VALIDATION

    private val validateEmailUseCase = ValidateEmailUseCase()
    private val validatePhoneNumber = ValidatePhoneNumberUseCase()
    private val validateText = ValidateNotEmptyUseCase()
    private val validateVat = ValidateVatUseCase()

    var formState by mutableStateOf(MainState())

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.FirstNameChanged -> {
                formState = formState.copy(firstName = event.firstName)
                setFirstName(event.firstName)
                validateFirstName()
            }

            is MainEvent.LastNameChanged -> {
                formState = formState.copy(lastName = event.lastName)
                setLastName(event.lastName)
                validateLastName()
            }

            is MainEvent.PhoneNumberChanged -> {
                formState = formState.copy(phoneNumber = event.phoneNumber)
                setPhoneNumber(event.phoneNumber)
                validatePhoneNumber()
            }

            is MainEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
                setEmail(event.email)
                validateEmail()
            }

            is MainEvent.StreetChanged -> {
                formState = formState.copy(street = event.street)
                setStreet(event.street)
                validateStreet()
            }

            is MainEvent.HouseNumberChanged -> {
                formState = formState.copy(houseNumber = event.houseNumber)
                setHouseNumber(event.houseNumber)
                validateHouseNumber()
            }

            is MainEvent.CityChanged -> {
                formState = formState.copy(city = event.city)
                setCity(event.city)
                validateCity()
            }

            is MainEvent.PostalCodeChanged -> {
                formState = formState.copy(postalCode = event.postalCode)
                setPostalCode(event.postalCode)
                validatePostalCode()
            }

            is MainEvent.VatChanged -> {
                formState = formState.copy(vat = event.vat.uppercase())
                setVatNumber(event.vat.uppercase())
                validateVat()
            }

            is MainEvent.Submit -> {
                if (validateFirstName() && validateLastName() && validatePhoneNumber() && validateEmail() && validateStreet() && validateHouseNumber() && validateCity() && validatePostalCode() && validateVat()) {
                    // TODO what is this?
                }
            }
        }
    }

    private fun validateFirstName(): Boolean {
        val result = validateText.execute(formState.firstName)
        formState = formState.copy(firstNameError = result.errorMessage)
        return result.successful
    }

    private fun validateLastName(): Boolean {
        val result = validateText.execute(formState.lastName)
        formState = formState.copy(lastNameError = result.errorMessage)
        return result.successful

    }

    private fun validatePhoneNumber(): Boolean {
        val result = validatePhoneNumber.execute(formState.phoneNumber)
        formState = formState.copy(phoneNumberError = result.errorMessage)
        return result.successful
    }

    private fun validateEmail(): Boolean {
        val emailResult = validateEmailUseCase.execute(formState.email)
        formState = formState.copy(emailError = emailResult.errorMessage)
        return emailResult.successful
    }

    private fun validateStreet(): Boolean {
        val result = validateText.execute(formState.street)
        formState = formState.copy(streetError = result.errorMessage)
        return result.successful
    }

    private fun validateHouseNumber(): Boolean {
        val result = validateText.execute(formState.houseNumber)
        formState = formState.copy(houseNumberError = result.errorMessage)
        return result.successful
    }

    private fun validateCity(): Boolean {
        val result = validateText.execute(formState.city)
        formState = formState.copy(cityError = result.errorMessage)
        return result.successful
    }

    private fun validatePostalCode(): Boolean {
        val result = validateText.execute(formState.postalCode)
        formState = formState.copy(postalCodeError = result.errorMessage)
        return result.successful
    }

    private fun validateVat(): Boolean {
        val result = validateVat.execute(formState.vat)
        formState = formState.copy(vatError = result.errorMessage)
        return result.successful
    }

    fun quotationScreenCanNavigate(): Boolean {
        return formState.isReadyForQuotation()
    }

    // ---------------------------------------- VALIDATION

    var extraMateriaalApiState: ExtraItemDetailsApiState by mutableStateOf(ExtraItemDetailsApiState.Loading)
        private set

    fun changeExtraItemAmount(item: ExtraItemState, amount: Int) =
        _quotationRequestState.value.equipments.find { it.extraItemId == item.extraItemId }
            ?.let { extraItem ->
                extraItem.amount = amount
            }

    fun getTotalPrice(): Double {
        return _quotationRequestState.value.equipments.sumOf { it.price * it.amount }
    }

    fun changeExtraItemEditing(item: ExtraItemState, editing: Boolean) =
        _quotationRequestState.value.equipments.find { it.extraItemId == item.extraItemId }
            ?.let { extraItem ->
                extraItem.isEditing = editing
            }

    fun addItemToCart(item: ExtraItemState) {

        val existingItem =
            _quotationRequestState.value.equipments.find { it.extraItemId == item.extraItemId }

        if (existingItem != null) {
            existingItem.amount = item.amount
        } else {
            _quotationRequestState.update {
                it.copy(equipments = it.equipments + item)
            }
        }
    }

    private fun getApiExtraEquipment() {
        viewModelScope.launch {
            try {
                val listResult = restApiRepository.getQuotationExtraEquipment()
                _quotationRequestState.update {
                    it.copy(equipments = listResult)
                }
                extraMateriaalApiState = ExtraItemDetailsApiState.Success(listResult)
            } catch (e: IOException) {
                val errorMessage = e.message ?: "An error occurred"

                extraMateriaalApiState = ExtraItemDetailsApiState.Error(errorMessage)
            }

        }
    }

    fun removeItemFromCart(item: ExtraItemState) {
        val existingItem =
            _quotationRequestState.value.equipments.find { it.extraItemId == item.extraItemId }
        if (existingItem != null) {
            changeExtraItemAmount(existingItem, 0)
            _quotationRequestState.update {
                it.copy(equipments = it.equipments - item)
            }
        }
    }


    fun getListAddedItems(): List<ExtraItemState> {
        return _quotationRequestState.value.equipments
    }


    fun getListSorted(index: Int): List<ExtraItemState> {
        val sortedList = when (index) {
            0 -> _quotationRequestState.value.equipments.sortedBy { it.price } // Sort asc
            1 -> _quotationRequestState.value.equipments.sortedByDescending { it.price } // Sort desc
            2 -> _quotationRequestState.value.equipments.sortedBy { it.title } // Sort by name asc
            3 -> _quotationRequestState.value.equipments.sortedByDescending { it.title } // Sort by name desc
            else -> throw IllegalArgumentException("Invalid index: $index")
        }
        return sortedList

    }


}

sealed class MainEvent {
    data class FirstNameChanged(val firstName: String) : MainEvent()
    data class LastNameChanged(val lastName: String) : MainEvent()
    data class EmailChanged(val email: String) : MainEvent()
    data class PhoneNumberChanged(val phoneNumber: String) : MainEvent()
    data class StreetChanged(val street: String) : MainEvent()
    data class HouseNumberChanged(val houseNumber: String) : MainEvent()
    data class CityChanged(val city: String) : MainEvent()
    data class PostalCodeChanged(val postalCode: String) : MainEvent()
    data class VatChanged(val vat: String) : MainEvent()
    object Submit : MainEvent()
}

data class MainState(
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
                vat.isNotEmpty() && vatError == null
    }
}
