package com.example.templateapplication.ui.screens.priceEstimation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
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
import com.example.templateapplication.model.guidePriceEstimation.EstimationDetails
import com.example.templateapplication.model.guidePriceEstimation.EstimationEquipment
import com.example.templateapplication.model.guidePriceEstimation.EstimationUiState
import com.example.templateapplication.model.guidePriceEstimation.PriceEstimationDetailsApiState
import com.example.templateapplication.network.restApi.priceEstimation.PriceEstimationResultApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Calendar

/**
 * ViewModel for managing and displaying price estimation details.
 *
 * Responsible for handling the fetching and management of price estimation details, including user inputs
 * and interaction with the Rest API and Google Maps API. It maintains the UI state related to price estimation
 * and provides the necessary data for UI components.
 *
 * @property restApiRepository Instance of [ApiRepository] used for making API calls.
 * @property googleMapsRepository Instance of [GoogleMapsRepository] used for Google Maps API calls.
 */
class PriceEstimationViewModel(
    private val restApiRepository: ApiRepository,
    private val googleMapsRepository: GoogleMapsRepository
) : ViewModel() {

    /**
     * StateFlow representing the UI state of estimation details.
     */
    private val _estimationDetailsState = MutableStateFlow(EstimationUiState())
    val estimationDetailsState = _estimationDetailsState.asStateFlow()

    /**
     * State variable to track the status of retrieving UI details via API.
     */
    var retrieveUiDetailsApiState: PriceEstimationDetailsApiState by mutableStateOf(
        PriceEstimationDetailsApiState.Loading
    )
        private set

    init {
        getApiEstimationDetails()
    }

    /**
     * Fetches estimation details from the API and updates the state of [estimationDetailsState].
     */
    private fun getApiEstimationDetails() {
        viewModelScope.launch {
            try {
                val result = restApiRepository.getEstimationDetails()
                val listDatesResult = restApiRepository.getUnavailableDateRanges()
                _estimationDetailsState.update {
                    it.copy(
                        dbData = EstimationDetails(
                            formulas = result.formulas,
                            equipment = result.equipment,
                            unavailableDates = listDatesResult,
                        )
                    )
                }
                retrieveUiDetailsApiState = PriceEstimationDetailsApiState.Success
            } catch (e: IOException) {
                val errorMessage = e.message ?: "An error occurred"
                retrieveUiDetailsApiState =
                    PriceEstimationDetailsApiState.Error(errorMessage)
            }

        }
    }

    /**
     * Factory for creating instances of [PriceEstimationViewModel].
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RestApiApplication)
                val guidePriceEstimationRepository =
                    application.container.apiRepository
                val googleMapsRepository = application.container.googleMapsRepository
                PriceEstimationViewModel(
                    restApiRepository = guidePriceEstimationRepository,
                    googleMapsRepository = googleMapsRepository
                )
            }
        }
    }

    /**
     * Selects a formula based on the provided ID and updates the UI state.
     *
     * @param id The ID of the selected formula.
     */
    fun selectFormula(id: Int) {
        _estimationDetailsState.update {
            it.copy(selectedFormula = id)
        }
    }

    /**
     * Sets the amount of people for the price estimation and updates the UI state.
     *
     * @param amountOfPeople String representation of the number of people.
     */
    fun setAmountOfPeople(amountOfPeople: String) {
        _estimationDetailsState.update {
            it.copy(amountOfPeople = amountOfPeople.toInt())
        }
    }

    /**
     * Sets the option for including Tripel beer in the estimation and updates the UI state.
     *
     * @param wantsTripelBeer Boolean indicating the choice for Tripel beer.
     */
    fun setWantsTripelBeer(wantsTripelBeer: Boolean) {
        _estimationDetailsState.update {
            it.copy(wantsTripelBeer = wantsTripelBeer)
        }
    }

    /**
     * Sets the option for including extra items in the estimation and updates the UI state.
     *
     * @param wantsExtras Boolean indicating the choice for extra items.
     */
    fun setWantsExtras(wantsExtras: Boolean) {
        _estimationDetailsState.update {
            it.copy(wantsExtras = wantsExtras)
        }
    }

    private var selectedExtras = listOf<EstimationEquipment>().toMutableStateList()

    /**
     * Toggles the checked state of an extra item in the estimation and updates the selection list.
     *
     * @param extraItem The [EstimationEquipment] item to be toggled.
     */
    fun extraItemsOnCheckedChange(extraItem: EstimationEquipment) {
        if (selectedExtras.contains(extraItem))
            selectedExtras.remove(extraItem)
        else
            selectedExtras.add(extraItem)
    }

    /**
     * Checks if an extra item is selected in the estimation.
     *
     * @param extraItem The [EstimationEquipment] item to be checked.
     * @return Boolean indicating if the item is selected.
     */
    fun hasSelectedExtraItem(extraItem: EstimationEquipment): Boolean {
        return selectedExtras.contains(extraItem)
    }

    /**
     * Updates the dropdown expansion state in the UI.
     *
     * @param value Boolean indicating the desired state of the dropdown.
     */
    fun setDropDownExpanded(value: Boolean) {
        _estimationDetailsState.update {
            it.copy(formulaDropDownIsExpanded = value)
        }
    }

    /**
     * Updates the date range for the price estimation.
     *
     * @param beginDate The start date in milliseconds since Unix epoch.
     * @param endDate The end date in milliseconds since Unix epoch.
     */
    fun updateDateRange(beginDate: Long?, endDate: Long?) {
        val begin = Calendar.getInstance()
        val end = Calendar.getInstance()

        if (beginDate != null) {
            begin.timeInMillis = beginDate
            if (endDate != null) end.timeInMillis = endDate else end.timeInMillis = beginDate
        }

        _estimationDetailsState.update {
            it.copy(startDate = begin, endDate = end)
        }
    }
    // ---------------------------------------- EVENT DETAILS: GOOGLE MAPS TODO code duplication?

    var googleMapsApiState: ApiResponse<GoogleMapsResponse> by mutableStateOf(
        ApiResponse.Loading
    )
        private set

    fun updateInput(input: String) {
        _estimationDetailsState.update {
            it.copy(googleMaps = it.googleMaps.copy(eventAddress = input))
        }
    }

    /**
     * Fetches predictions from Google Maps based on the input address.
     */
    fun getPredictions() {
        viewModelScope.launch {
            try {
                val listResult =
                    googleMapsRepository.getPredictions(input = _estimationDetailsState.value.googleMaps.eventAddress)
                _estimationDetailsState.update {
                    it.copy(googleMaps = it.googleMaps.copy(predictionsResponse = listResult))
                }
                googleMapsApiState = ApiResponse.Success(
                    GoogleMapsResponse(predictionsResponse = listResult)
                )
                Log.i("AAAAAAAA", listResult.toString())
            } catch (e: IOException) {
                googleMapsApiState = ApiResponse.Error
                Log.i("Error", e.toString())
            }
        }
    }

    /**
     * Determines if a valid place has been found based on the predictions.
     *
     * @return Boolean indicating if a valid place is found.
     */
    fun placeFound(): Boolean {
        return if (_estimationDetailsState.value.placeResponse.candidates.isNotEmpty())
            _estimationDetailsState.value.placeResponse.candidates[0].formatted_address.isNotEmpty()
        else false
    }

    var calculatePriceApiState: PriceEstimationResultApiState by mutableStateOf(
        PriceEstimationResultApiState.Idle
    )
        private set

    /**
     * Initiates the price estimation process and updates the state based on the result.
     */
    fun getPriceEstimation() {
        viewModelScope.launch {
            try {
                Log.i("PriceEstimationViewModel getPriceEstimation", "Sending request to api..")
                val response = restApiRepository.calculatePrice(
                    _estimationDetailsState.value.selectedFormula,
                    selectedExtras.toList().map {
                        it.id
                    },
                    _estimationDetailsState.value.startDate!!.toInstant().toEpochMilli().toString(),
                    _estimationDetailsState.value.endDate!!.toInstant().toEpochMilli().toString(),
                    _estimationDetailsState.value.amountOfPeople,
                    _estimationDetailsState.value.wantsTripelBeer,
                ).estimatedPrice

                calculatePriceApiState = PriceEstimationResultApiState.Success(BigDecimal(response).setScale(2, RoundingMode.CEILING))
            } catch (e: HttpException) {
                val errorMessage = e.message ?: "Post request failed"
                Log.e(
                    "RestApi sendQuotationRequest",
                    errorMessage
                )
                calculatePriceApiState = PriceEstimationResultApiState.Error(errorMessage)
            }
        }
    }
}