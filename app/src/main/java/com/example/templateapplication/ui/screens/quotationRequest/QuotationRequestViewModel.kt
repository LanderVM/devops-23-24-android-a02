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
import com.example.templateapplication.model.adres.ApiResponse
import com.example.templateapplication.model.common.googleMaps.GoogleMapsResponse
import com.example.templateapplication.model.common.quotation.Formula
import com.example.templateapplication.model.common.quotation.FormulaListState
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

/**
 * ViewModel for handling quotation request-related functionalities.
 *
 * Manages the data and operations for creating and processing quotation requests. Interacts with
 * REST API for quotation data and Google Maps API for location services. It maintains the state of
 * various API calls and manages the UI state for quotation requests.
 *
 * @property restApiRepository Instance of [ApiRepository] used for REST API calls.
 * @property googleMapsRepository Instance of [GoogleMapsRepository] used for Google Maps API calls.
 */
class QuotationRequestViewModel(
    private val restApiRepository: ApiRepository,
    private val googleMapsRepository: GoogleMapsRepository
) : ViewModel() {

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

    /**
     * A [MutableStateFlow] managing the state of the quotation request.
     */
    private val _quotationRequestState = MutableStateFlow(QuotationRequestState())
    val quotationRequestState = _quotationRequestState.asStateFlow()

    /**
     * A [MutableStateFlow] managing the state of the formula list.
     */
    private val _formulaState = MutableStateFlow(FormulaListState())
    val formulaState = _formulaState.asStateFlow()

    /**
     * A [MutableStateFlow] managing the UI state for the quotation request.
     */
    private val _quotationUiState = MutableStateFlow(QuotationUiState())
    val quotationUiState = _quotationUiState.asStateFlow()

    /**
     * State variable to track the status of date range API calls.
     */
    private var dateRangesApiState: DateRangesApiState by mutableStateOf(DateRangesApiState.Loading)

    /**
     * Updates the date range for the quotation request.
     *
     * @param beginDate The start date in milliseconds since the Unix epoch.
     * @param endDate The end date in milliseconds since the Unix epoch.
     */
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

    /**
     * Retrieves a formatted date range string for the quotation request.
     *
     * @return A string representing the date range in "dd/MM/yyyy" format.
     */
    fun getDateRange(): String {
        if (_quotationRequestState.value.startTime == null || _quotationRequestState.value.endTime == null) {
            return "/"
        }
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)

        return "${dateFormat.format(_quotationRequestState.value.startTime!!.timeInMillis)} - " +
                dateFormat.format(_quotationRequestState.value.endTime!!.timeInMillis)
    }

    /**
     * Fetches unavailable date ranges from the API and updates the UI state.
     */
    private fun getDateRanges() {
        viewModelScope.launch {
            dateRangesApiState = try {
                val listDatesResult = restApiRepository.getUnavailableDateRanges()
                _quotationUiState.update {
                    it.copy(listDateRanges = listDatesResult)
                }
                DateRangesApiState.Success(listDatesResult)
            } catch (e: IOException) {
                val errorMessage = e.message ?: "An error occurred"
                Log.e(
                    "RestApi getDateRanges",
                    e.message ?: "Failed to retrieve date ranges from api"
                )
                DateRangesApiState.Error(errorMessage)
            }

        }
    }

    /**
     * State variable to track the status of the post quotation request API call.
     */
    var postQuotationRequestApiState: ApiQuotationRequestPostApiState by mutableStateOf(
        ApiQuotationRequestPostApiState.Idle
    )
        private set

    /**
     * Sends a quotation request to the server and updates the [postQuotationRequestApiState] based on the result.
     */
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
                    _quotationRequestState.value.formula!!.id,
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

    /**
     * Calculates the basic price of the formula based on the selected duration.
     *
     * @return The basic formula price as a [Double].
     */
    fun getPriceBasicFormula(): Double {

        val initialPrice: Double

        val start = quotationRequestState.value.startTime
        val end = quotationRequestState.value.endTime
        val amountOfDaysDiff =
            ((end!!.timeInMillis - start!!.timeInMillis) / (24 * 60 * 60 * 1000)).toInt()


        val formula = quotationRequestState.value.formula
        initialPrice = when (amountOfDaysDiff) {
            0 -> formula!!.basePrice[0]
            1 -> formula!!.basePrice[1]
            2 -> formula!!.basePrice[2]
            else -> {
                val extraDays = amountOfDaysDiff - 2
                formula!!.pricePerDayExtra * extraDays + formula.basePrice[2]
            }
        }
        val formattedValue = String.format("%.2f", initialPrice)
        return formattedValue.toDouble()
    }

    /**
     * Calculates transportation costs based on the distance.
     *
     * @return The transportation cost as a [Double].
     */
    fun calulateTransportCosts(): Double {
        val distance = getDistanceLong().div(1000)

        var cost = 0.00
        return if (distance <= 20.0) {
            cost
        } else {
            cost = (distance - 20) * 0.75
            cost
        }
    }

    /**
     * Calculates the price for beer based on the number of people and beer preference.
     *
     * @return The calculated price for beer.
     */
    fun calculatePriceBeer(): Double {
        val price = if (quotationRequestState.value.isTripelBier) {
            3.0
        } else {
            1.5
        }
        return quotationRequestState.value.numberOfPeople.times(price)
    }

    /**
     * Calculates the price for BBQ services based on the number of people.
     *
     * @return The calculated price for BBQ.
     */
    fun calculatePriceBbq(): Double {
        return quotationRequestState.value.numberOfPeople.times(12.0)
    }

    /**
     * Computes the total price without VAT for the quotation request.
     *
     * @return The total price excluding VAT.
     */
    fun getTotalPriceWithoutVat(): Double {
        val formula = quotationRequestState.value.formula
        var totalCost =
            getListAddedItems().sumOf { a -> a.price * a.amount } + getPriceBasicFormula() + calulateTransportCosts()
        if (formula!!.id == 2 || formula.id == 3) {
            totalCost += calculatePriceBeer()
        }
        if (formula.id == 3) {
            totalCost += calculatePriceBbq()
        }
        return totalCost
    }

    /**
     * Calculates the total VAT for the quotation request.
     *
     * @return The total VAT amount.
     */
    fun getTotalVat(): Double {
        val formula = quotationRequestState.value.formula
        val totalVat: Double
        var totalPrice = getTotalPriceWithoutVat()
        val beerPrice = calculatePriceBeer()
        val bbqPrice = calculatePriceBbq()
        if (formula!!.id == 2 || formula.id == 3) {
            totalPrice -= beerPrice
        }
        if (formula.id == 3) {
            totalPrice -= bbqPrice
        }
        totalVat = (totalPrice * 0.21) + (beerPrice * 0.12) + (bbqPrice * 0.12)
        return totalVat
    }

    /**
     * Selects a formula for the quotation request and updates the UI state.
     *
     * @param formula The [Formula] selected.
     */
    fun selectFormula(formula: Formula) {
        _quotationRequestState.update {
            it.copy(formula = formula)
        }
    }

    /**
     * Updates the preference for Tripel Beer in the quotation request state.
     *
     * @param wantsTripelBeer Indicates the preference for Tripel Beer (1 for yes).
     */
    fun selectBeer(wantsTripelBeer: Int) {
        _quotationRequestState.update {
            it.copy(isTripelBier = wantsTripelBeer == 1)
        }
    }

    /**
     * Expands or collapses the dropdown in the UI.
     *
     * @param value Boolean indicating the desired state of the dropdown.
     */
    fun setDropDownExpanded(value: Boolean) {
        _quotationUiState.update {
            it.copy(dropDownExpanded = value)
        }
    }

    /**
     * Sets the number of people for the quotation request.
     *
     * @param amountOfPeople The number of people as a String.
     */
    fun setAmountOfPeople(amountOfPeople: String) {
        _quotationRequestState.update {
            it.copy(numberOfPeople = amountOfPeople.toInt())
        }
    }

    // ---------------------------------------- EVENT DETAILS: GOOGLE MAPS
    /**
     * State variable to track the status of Google Maps API calls.
     */
    var googleMapsApiState: ApiResponse<GoogleMapsResponse> by mutableStateOf(
        ApiResponse.Loading
    )
        private set

    /**
     * Sets the input address for Google Maps predictions.
     *
     * @param input The address input string.
     */
    private fun setAddressInput(input: String) {
        _quotationUiState.update {
            it.copy(googleMaps = it.googleMaps.copy(eventAddress = input))
        }
    }

    /**
     * Fetches address predictions using Google Maps API.
     */
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

    /**
     * Updates the distance data for the quotation request using Google Maps API.
     */
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

    /**
     * Updates the location marker based on the address input.
     */
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

    /**
     * Retrieves the distance for the event location using Google Maps API.
     *
     * @return The distance as a Long value.
     */
    fun getDistanceLong(): Long {
        return _quotationUiState.value.googleMaps.distanceResponse.rows[0].elements[0].distance.value
    }

    /**
     * Checks if a valid place has been found based on Google Maps predictions.
     *
     * @return Boolean indicating if a valid place is found.
     */
    fun placeFound(): Boolean {
        return if (_quotationUiState.value.googleMaps.eventAddressAutocompleteCandidates.candidates.isNotEmpty())
            _quotationUiState.value.googleMaps.eventAddressAutocompleteCandidates.candidates[0].formatted_address.isNotEmpty()
        else false
    }

    // ----------------------------------------  PERSONAL DETAILS

    /**
     * Updates the first name in the quotation request state.
     *
     * @param firstName The first name to set.
     */
    private fun setFirstName(firstName: String) {
        _quotationRequestState.update {
            it.copy(
                customer = it.customer.copy(
                    firstName = firstName
                )
            )
        }
    }

    /**
     * Updates the last name in the quotation request state.
     *
     * @param lastName The last name to set.
     */
    private fun setLastName(lastName: String) {
        _quotationRequestState.update {
            it.copy(
                customer = it.customer.copy(
                    lastName = lastName
                )
            )
        }
    }

    /**
     * Updates the phone number in the quotation request state.
     *
     * @param phoneNumber The phone number to set.
     */
    private fun setPhoneNumber(phoneNumber: String) {
        _quotationRequestState.update {
            it.copy(
                customer = it.customer.copy(
                    phoneNumber = phoneNumber
                )
            )
        }
    }

    /**
     * Updates the email in the quotation request state.
     *
     * @param email The email to set.
     */
    private fun setEmail(email: String) {
        _quotationRequestState.update {
            it.copy(
                customer = it.customer.copy(
                    email = email
                )
            )
        }
    }

    /**
     * Updates the street in the quotation request state.
     *
     * @param street The street to set.
     */
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

    /**
     * Updates the house number in the quotation request state.
     *
     * @param houseNumber The house number to set.
     */
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

    /**
     * Updates the city in the quotation request state.
     *
     * @param city The city to set.
     */
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

    /**
     * Updates the postal code in the quotation request state.
     *
     * @param postalCode The postal code to set.
     */
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

    /**
     * Updates the vat number in the quotation request state.
     *
     * @param vatNumber The vat number to set.
     */
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

    /**
     * State variable for managing the form state of user input.
     */
    var formState by mutableStateOf(MainState())

    /**
     * Handles events triggered by user interactions in the UI.
     *
     * Updates the form state based on the type of event and performs necessary validations
     * and state updates.
     *
     * @param event The [MainEvent] representing the user interaction.
     */
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

    /**
     * Validates the address and updates the form state.
     *
     * @return Boolean indicating if the address is valid.
     */
    private fun validateAddress(): Boolean {
        val result = validateText.execute(formState.address)
        formState = formState.copy(addressError = result.errorMessage)
        return result.successful
    }

    /**
     * Validates the number of people and updates the form state.
     *
     * @return Boolean indicating if the number of people is valid.
     */
    private fun validateNumberOfPeople(): Boolean {
        val result = validateNumber.execute(formState.numberOfPeople)
        formState = formState.copy(numberOfPeopleError = result.errorMessage)
        return result.successful
    }

    /**
     * Validates the first name and updates the form state.
     *
     * @return Boolean indicating if the first name is valid.
     */
    private fun validateFirstName(): Boolean {
        val result = validateText.execute(formState.firstName)
        formState = formState.copy(firstNameError = result.errorMessage)
        return result.successful
    }

    /**
     * Validates the last name and updates the form state.
     *
     * @return Boolean indicating if the last name is valid.
     */
    private fun validateLastName(): Boolean {
        val result = validateText.execute(formState.lastName)
        formState = formState.copy(lastNameError = result.errorMessage)
        return result.successful

    }

    /**
     * Validates the phone number and updates the form state.
     *
     * @return Boolean indicating if the phone number is valid.
     */
    private fun validatePhoneNumber(): Boolean {
        val result = validatePhoneNumber.execute(formState.phoneNumber)
        formState = formState.copy(phoneNumberError = result.errorMessage)
        return result.successful
    }

    /**
     * Validates the email and updates the form state.
     *
     * @return Boolean indicating if the email is valid.
     */
    private fun validateEmail(): Boolean {
        val result = validateEmailUseCase.execute(formState.email)
        formState = formState.copy(emailError = result.errorMessage)
        return result.successful
    }

    /**
     * Validates the street and updates the form state.
     *
     * @return Boolean indicating if the street is valid.
     */
    private fun validateStreet(): Boolean {
        val result = validateText.execute(formState.street)
        formState = formState.copy(streetError = result.errorMessage)
        return result.successful
    }

    /**
     * Validates the house number and updates the form state.
     *
     * @return Boolean indicating if the house number is valid.
     */
    private fun validateHouseNumber(): Boolean {
        val result = validateText.execute(formState.houseNumber)
        formState = formState.copy(houseNumberError = result.errorMessage)
        return result.successful
    }

    /**
     * Validates the city and updates the form state.
     *
     * @return Boolean indicating if the city is valid.
     */
    private fun validateCity(): Boolean {
        val result = validateText.execute(formState.city)
        formState = formState.copy(cityError = result.errorMessage)
        return result.successful
    }

    /**
     * Validates the postal code and updates the form state.
     *
     * @return Boolean indicating if the postal code is valid.
     */
    private fun validatePostalCode(): Boolean {
        val result = validateText.execute(formState.postalCode)
        formState = formState.copy(postalCodeError = result.errorMessage)
        return result.successful
    }

    /**
     * Validates the vat number and updates the form state.
     *
     * @return Boolean indicating if the vat number is valid.
     */
    private fun validateVat(): Boolean {
        val result = validateVat.execute(formState.vat)
        formState = formState.copy(vatError = result.errorMessage)
        return result.successful
    }

    /**
     * Determines if the quotation screen can proceed to the next step.
     *
     * @return Boolean indicating if navigation is allowed.
     */
    fun quotationScreenCanNavigate(): Boolean {
        return formState.isReadyForQuotation()
    }

    /**
     * Determines if the personal details screen can proceed to the next step.
     *
     * @return Boolean indicating if navigation is allowed.
     */
    fun personalDetailScreenCanNavigate(): Boolean {
        return formState.isReadyForPersonalDetails()
    }

    // ---------------------------------------- VALIDATION

    /**
     * State variable to track the status of additional material API calls.
     */
    var extraMateriaalApiState: ApiResponse<List<ExtraItemState>> by mutableStateOf(ApiResponse.Loading)
        private set

    /**
     * Changes the amount of an extra item in the user's quotation.
     *
     * @param item The [ExtraItemState] to be updated.
     * @param amount The new amount for the item.
     */
    fun changeExtraItemAmount(item: ExtraItemState, amount: Int) =
        _quotationUiState.value.extraItems.find { it.extraItemId == item.extraItemId }
            ?.let { extraItem ->
                extraItem.amount = amount
            }

    /**
     * Adds an item to the shopping cart in the quotation request state.
     *
     * @param item The [ExtraItemState] representing the item to add.
     */
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

    /**
     * Fetches extra equipment data from the API and updates the UI state.
     *
     * Initiates an API call to retrieve additional equipment options for the quotation
     * and updates the [extraMateriaalApiState] based on the result.
     */
    private fun getApiExtraEquipment() {
        viewModelScope.launch {
            extraMateriaalApiState = try {
                val listResult = restApiRepository.getQuotationExtraEquipment()
                _quotationUiState.update {
                    it.copy(extraItems = listResult)
                }
                ApiResponse.Success(listResult)
            } catch (e: IOException) {
                ApiResponse.Error
            }
        }
    }

    /**
     * Removes an item to the shopping cart in the quotation request state.
     *
     * @param item The [ExtraItemState] representing the item to remove.
     */
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

    /**
     * Retrieves a list of added items in the shopping cart.
     *
     * @return List of [ExtraItemState] representing added items.
     */
    fun getListAddedItems(): List<ExtraItemState> {
        return _quotationRequestState.value.equipments
    }

    /**
     * Returns a sorted list of extra items based on the specified sorting criteria.
     *
     * @param index The sorting criterion index.
     * @return Sorted list of [ExtraItemState].
     * @throws IllegalArgumentException If the index is invalid.
     */
    fun getListSorted(index: Int): List<ExtraItemState> {
        val sortedList = when (index) {
            0 -> _quotationUiState.value.extraItems.sortedByDescending { it.price } // Sort asc
            1 -> _quotationUiState.value.extraItems.sortedBy { it.price } // Sort desc
            2 -> _quotationUiState.value.extraItems.sortedBy { it.title } // Sort by name asc
            3 -> _quotationUiState.value.extraItems.sortedByDescending { it.title } // Sort by name desc
            else -> throw IllegalArgumentException("Invalid index: $index")
        }
        return sortedList
    }
}