package com.example.templateapplication.ui.screens.quotationRequest

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
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
import com.example.templateapplication.model.quotationRequest.ExtraItemState
import com.example.templateapplication.model.quotationRequest.QuotationRequestState
import com.example.templateapplication.model.quotationRequest.QuotationUiState
import com.example.templateapplication.validation.ValidateEmailUseCase
import com.example.templateapplication.validation.ValidatePhoneNumberUseCase
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
        return true // TODO navigate using validation per page
    }

    // ---------------------------------------- EVENT DETAILS: EVENT DETAILS
    private val _quotationRequestState = MutableStateFlow(QuotationRequestState())
    val quotationRequestState = _quotationRequestState.asStateFlow()

    private val _quotationUiState = MutableStateFlow(QuotationUiState())
    val quotationUiState = _quotationUiState.asStateFlow()

    fun updateDateRange(beginDate: Long?, endDate: Long?) {
        val begin = Calendar.getInstance()
        val end = Calendar.getInstance()

        if (beginDate != null) {
            begin.timeInMillis = beginDate
            if (endDate != null) end.timeInMillis = endDate else end.timeInMillis = beginDate
        }

        _quotationRequestState.update {
            it.copy(startDate = begin, endDate = end)
        }
    }


    fun getDateRange(): String {
        if (_quotationRequestState.value.startDate == null || _quotationRequestState.value.endDate == null) {
            return "/"
        }
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)

        return "${dateFormat.format(_quotationRequestState.value.startDate!!.timeInMillis)} - " +
                dateFormat.format(_quotationRequestState.value.endDate!!.timeInMillis)
    }

    // TODO validate each field separately. Only check if hasErrors = true here.

    fun selectFormula(id: Int) {
        _quotationRequestState.update {
            it.copy(selectedFormula = id)
        }
    }

    fun selectBeer(wantsTripelBeer: Int) {
        _quotationRequestState.update {
            it.copy(wantsTripelBeer = wantsTripelBeer == 1)
        }
    }

    fun setDropDownExpanded(value: Boolean) {
        _quotationUiState.update {
            it.copy(dropDownExpanded = value)
        }
    }

    fun setAmountOfPeople(amountOfPeople: String) {
        if (amountOfPeople.isDigitsOnly()) {
            _quotationRequestState.update {
                it.copy(amountOfPeople = amountOfPeople)
            }
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
                        eventPlaats = _quotationRequestState.value.placeResponse.candidates[0].formatted_address
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
                _quotationRequestState.update {
                    it.copy(placeResponse = placeResult)
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
        return if (_quotationRequestState.value.placeResponse.candidates.isNotEmpty())
            _quotationRequestState.value.placeResponse.candidates[0].formatted_address.isNotEmpty()
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
        }    }

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

    var formState by mutableStateOf(MainState())

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
                setEmail(event.email)
                validateEmail()
            }

            is MainEvent.PhoneNumberChanged -> {
                formState = formState.copy(phoneNumber = event.phoneNumber)
                setPhoneNumber(event.phoneNumber)
                validatePhoneNumber()
            }

            is MainEvent.Submit -> {
                if (validateEmail() && validatePhoneNumber()) {

                }
            }
        }
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

    // ---------------------------------------- VALIDATION

    var extraMateriaalApiState: ExtraItemDetailsApiState by mutableStateOf(ExtraItemDetailsApiState.Loading)
        private set

    fun changeExtraItemAmount(item: ExtraItemState, amount: Int) =
        _quotationRequestState.value.addedItems.find { it.extraItemId == item.extraItemId }
            ?.let { extraItem ->
                extraItem.amount = amount
            }

    fun getTotalPrice(): Double {
        return _quotationRequestState.value.addedItems.sumOf { it.price * it.amount }
    }

    fun changeExtraItemEditing(item: ExtraItemState, editing: Boolean) =
        _quotationRequestState.value.addedItems.find { it.extraItemId == item.extraItemId }
            ?.let { extraItem ->
                extraItem.isEditing = editing
            }

    fun addItemToCart(item: ExtraItemState) {

        val existingItem = _quotationRequestState.value.addedItems.find { it.extraItemId == item.extraItemId }

        if (existingItem != null) {
            existingItem.amount = item.amount
        } else {
            _quotationRequestState.update {
                it.copy(addedItems = it.addedItems + item)
            }
        }
    }

    private fun getApiExtraEquipment() {
        viewModelScope.launch {
            try {
                val listResult = restApiRepository.getQuotationExtraEquipment()
                _quotationRequestState.update {
                    it.copy(addedItems = listResult)
                }
                extraMateriaalApiState = ExtraItemDetailsApiState.Success(listResult)
            } catch (e: IOException) {
                val errorMessage = e.message ?: "An error occurred"

                extraMateriaalApiState = ExtraItemDetailsApiState.Error(errorMessage)
            }

        }
    }

    fun removeItemFromCart(item: ExtraItemState) {
        val existingItem = _quotationRequestState.value.addedItems.find { it.extraItemId == item.extraItemId }
        if (existingItem != null) {
            changeExtraItemAmount(existingItem, 0)
            _quotationRequestState.update {
                it.copy(addedItems = it.addedItems - item)
            }
        }
    }


    fun getListAddedItems(): List<ExtraItemState> {
        return _quotationRequestState.value.addedItems
    }


    fun getListSorted(index: Int): List<ExtraItemState> {
        val sortedList = when (index) {
            0 -> _quotationRequestState.value.addedItems.sortedBy { it.price } // Sort asc
            1 -> _quotationRequestState.value.addedItems.sortedByDescending { it.price } // Sort desc
            2 -> _quotationRequestState.value.addedItems.sortedBy { it.title } // Sort by name asc
            3 -> _quotationRequestState.value.addedItems.sortedByDescending { it.title } // Sort by name desc
            else -> throw IllegalArgumentException("Invalid index: $index")
        }
        return sortedList

    }


}

sealed class MainEvent {
    data class EmailChanged(val email: String) : MainEvent()
    data class PhoneNumberChanged(val phoneNumber: String) : MainEvent()
    object Submit : MainEvent()
}

data class MainState(
    val email: String = "",
    val phoneNumber: String = "",
    val emailError: UiText? = null,
    val phoneNumberError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val isVisiblePassword: Boolean = false
)
