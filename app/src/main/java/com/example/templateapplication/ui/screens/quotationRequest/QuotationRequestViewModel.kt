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
import com.example.templateapplication.model.quotationRequest.DateRangesApiState
import com.example.templateapplication.model.quotationRequest.ExtraItemState
import com.example.templateapplication.model.quotationRequest.QuotationRequestState
import com.example.templateapplication.model.quotationRequest.QuotationUiState
import com.example.templateapplication.model.quotationRequest.parseAddress
import com.example.templateapplication.network.restApi.quotationRequest.Address
import com.example.templateapplication.network.restApi.quotationRequest.ApiQuotationRequestPost
import com.example.templateapplication.network.restApi.quotationRequest.ApiQuotationRequestPostApiState
import com.example.templateapplication.network.restApi.quotationRequest.Customer
import com.example.templateapplication.network.restApi.quotationRequest.Email
import com.example.templateapplication.network.restApi.quotationRequest.EquipmentSelected
import com.example.templateapplication.validation.MainEvent
import com.example.templateapplication.validation.MainState
import com.example.templateapplication.validation.ValidateEmailUseCase
import com.example.templateapplication.validation.ValidateNotEmptyUseCase
import com.example.templateapplication.validation.ValidatePhoneNumberUseCase
import com.example.templateapplication.validation.ValidateRequiredNumberUseCase
import com.example.templateapplication.validation.ValidateVatUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
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
                val listDatesResult = restApiRepository.getUnavailableDateRanges()
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

    var postQuotationRequestApiState: ApiQuotationRequestPostApiState by mutableStateOf(
        ApiQuotationRequestPostApiState.Idle
    )
        private set

    fun sendQuotationRequest() {
        viewModelScope.launch {
            try {
                Log.i("QuotationRequestViewModel sendQuotationRequest", "Preparing body..")
                _quotationRequestState.update {
                    it.copy(eventLocation = parseAddress(_quotationUiState.value.googleMaps.eventAddressAutocompleteCandidates.candidates[0].formatted_address))
                }
                Log.i(
                    "QuotationRequestViewModel sendQuotationRequest",
                    "Set eventLocation property to ${_quotationRequestState.value.eventLocation}"
                )
                Log.i(
                    "QuotationRequestViewModel sendQuotationRequest",
                    "Creating post request body from Request state.."
                )
                val body = ApiQuotationRequestPost(
                    null,
                    _quotationRequestState.value.formulaId,
                    Address(
                        _quotationRequestState.value.eventLocation.street,
                        _quotationRequestState.value.eventLocation.houseNumber,
                        _quotationRequestState.value.eventLocation.postalCode,
                        _quotationRequestState.value.eventLocation.city,
                    ),
                    _quotationRequestState.value.startTime?.toInstant().toString(),
                    _quotationRequestState.value.endTime?.toInstant().toString(),
                    _quotationRequestState.value.equipments.map {
                        EquipmentSelected(it.extraItemId, it.amount)
                    },
                    Customer(
                        null,
                        _quotationRequestState.value.customer.firstName,
                        _quotationRequestState.value.customer.lastName,
                        Email(
                            _quotationRequestState.value.customer.email,
                        ),
                        Address(
                            _quotationRequestState.value.customer.billingAddress.street,
                            _quotationRequestState.value.customer.billingAddress.houseNumber,
                            _quotationRequestState.value.customer.billingAddress.postalCode,
                            _quotationRequestState.value.customer.billingAddress.city,
                        ),
                        _quotationRequestState.value.customer.phoneNumber,
                        _quotationRequestState.value.customer.vatNumber,
                    ),
                    _quotationRequestState.value.isTripelBier,
                    _quotationRequestState.value.numberOfPeople,
                )
                Log.i(
                    "QuotationRequestViewModel sendQuotationRequest",
                    "Post request body created: $body"
                )
                Log.i("QuotationRequestViewModel sendQuotationRequest", "Sending request to api..")
                val response =
                    restApiRepository.postQuotationRequest(body).execute()
                // TODO fix response bug (see discord #android)
                // TODO confirmed popup if quotation was sent in

                if (!response.isSuccessful) throw IOException(
                    response.errorBody().toString()
                ) // TODO proper showing of error in ui

                postQuotationRequestApiState =
                    ApiQuotationRequestPostApiState.Success
            } catch (e: IOException) {
                val errorMessage = e.message ?: "Post request failed"
                Log.e(
                    "RestApi sendQuotationRequest",
                    errorMessage
                )
                postQuotationRequestApiState = ApiQuotationRequestPostApiState.Error(errorMessage)
            } catch (e: HttpException) {
                val errorMessage = e.message ?: "Post request failed"
                Log.e(
                    "RestApi sendQuotationRequest",
                    errorMessage
                )
                postQuotationRequestApiState = ApiQuotationRequestPostApiState.Error(errorMessage)
            } catch (e: Exception) { // TODO als tijd over
                postQuotationRequestApiState =
                    ApiQuotationRequestPostApiState.Success
            }
        }
    }

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

    fun setAddressInput(input: String) {
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

    fun placeFound(): Boolean {
        return if (_quotationUiState.value.googleMaps.eventAddressAutocompleteCandidates.candidates.isNotEmpty())
            _quotationUiState.value.googleMaps.eventAddressAutocompleteCandidates.candidates[0].formatted_address.isNotEmpty()
        else false
    }

    // ----------------------------------------  PERSONAL DETAILS

    private fun setFirstName(firstName: String) {
        _quotationRequestState.update {
            it.copy(
                customer = it.customer.copy(
                    firstName = firstName
                )
            )
        }
    }

    private fun setLastName(lastName: String) {
        _quotationRequestState.update {
            it.copy(
                customer = it.customer.copy(
                    lastName = lastName
                )
            )
        }
    }

    private fun setPhoneNumber(phoneNumber: String) {
        _quotationRequestState.update {
            it.copy(
                customer = it.customer.copy(
                    phoneNumber = phoneNumber
                )
            )
        }
    }

    private fun setEmail(email: String) {
        _quotationRequestState.update {
            it.copy(
                customer = it.customer.copy(
                    email = email
                )
            )
        }
    }

    private fun setStreet(street: String) {
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

    private fun setHouseNumber(houseNumber: String) {
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

    private fun setCity(city: String) {
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

    private fun setPostalCode(postalCode: String) {
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

    private fun setVatNumber(vatNumber: String) {
        _quotationRequestState.update {
            it.copy(
                customer = it.customer.copy(
                    vatNumber = vatNumber
                )
            )
        }
    }

// ---------------------------------------- VALIDATION

    private val validateNumber = ValidateRequiredNumberUseCase()
    private val validateEmailUseCase = ValidateEmailUseCase()
    private val validatePhoneNumber = ValidatePhoneNumberUseCase()
    private val validateText = ValidateNotEmptyUseCase()
    private val validateVat = ValidateVatUseCase()

    var formState by mutableStateOf(MainState())

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.AddressChanged -> {
                formState = formState.copy(address = event.address)
                setAddressInput(event.address)
                validateAddress()
            }

            is MainEvent.NumberOfPeopleChanged -> {
                formState = formState.copy(numberOfPeople = event.numberOfPeople)
                if (validateNumberOfPeople()) {
                    setAmountOfPeople(event.numberOfPeople)
                }
            }

            is MainEvent.FirstNameChanged -> {
                formState = formState.copy(firstName = event.firstName)
                if (validateFirstName()) {
                    setFirstName(event.firstName)
                }
            }

            is MainEvent.LastNameChanged -> {
                formState = formState.copy(lastName = event.lastName)
                if (validateLastName()) {
                    setLastName(event.lastName)
                }
            }

            is MainEvent.PhoneNumberChanged -> {
                formState = formState.copy(phoneNumber = event.phoneNumber)
                if (validatePhoneNumber()) {
                    setPhoneNumber(event.phoneNumber)
                }
            }

            is MainEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
                if (validateEmail()) {
                    setEmail(event.email)
                }
            }

            is MainEvent.StreetChanged -> {
                formState = formState.copy(street = event.street)
                if (validateStreet()) {
                    setStreet(event.street)
                }
            }

            is MainEvent.HouseNumberChanged -> {
                formState = formState.copy(houseNumber = event.houseNumber)
                if (validateHouseNumber()) {
                    setHouseNumber(event.houseNumber)
                }
            }

            is MainEvent.CityChanged -> {
                formState = formState.copy(city = event.city)
                if (validateCity()) {
                    setCity(event.city)
                }
            }

            is MainEvent.PostalCodeChanged -> {
                formState = formState.copy(postalCode = event.postalCode)
                if (validatePostalCode()) {
                    setPostalCode(event.postalCode)
                }
            }

            is MainEvent.VatChanged -> {
                formState = formState.copy(vat = event.vat.uppercase())
                if (validateVat()) {
                    setVatNumber(event.vat.uppercase())
                }
            }
        }
    }

    private fun validateAddress(): Boolean {
        val result = validateText.execute(formState.address)
        formState = formState.copy(addressError = result.errorMessage)
        return result.successful
    }

    private fun validateNumberOfPeople(): Boolean {
        val result = validateNumber.execute(formState.numberOfPeople)
        formState = formState.copy(numberOfPeopleError = result.errorMessage)
        return result.successful
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
        val result = validateEmailUseCase.execute(formState.email)
        formState = formState.copy(emailError = result.errorMessage)
        return result.successful
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

    fun personalDetailScreenCanNavigate(): Boolean {
        return formState.isReadyForPersonalDetails()
    }

    // ---------------------------------------- VALIDATION

    var extraMateriaalApiState: ApiResponse<List<ExtraItemState>> by mutableStateOf(ApiResponse.Loading)
        private set

    fun changeExtraItemAmount(item: ExtraItemState, amount: Int) =
        _quotationUiState.value.extraItems.find { it.extraItemId == item.extraItemId }
            ?.let { extraItem ->
                extraItem.amount = amount
            }

    fun getTotalPrice(): Double {
        return _quotationUiState.value.extraItems.sumOf { a -> a.price * a.extraItemId }
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
                _quotationUiState.update {
                    it.copy(extraItems = listResult)
                }
                extraMateriaalApiState = ApiResponse.Success(listResult)
            } catch (e: IOException) {
                extraMateriaalApiState = ApiResponse.Error
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
            0 -> _quotationUiState.value.extraItems.sortedByDescending  { it.price } // Sort asc
            1 -> _quotationUiState.value.extraItems.sortedBy{ it.price } // Sort desc
            2 -> _quotationUiState.value.extraItems.sortedBy { it.title } // Sort by name asc
            3 -> _quotationUiState.value.extraItems.sortedByDescending { it.title } // Sort by name desc
            else -> throw IllegalArgumentException("Invalid index: $index")
        }
        return sortedList
    }
}