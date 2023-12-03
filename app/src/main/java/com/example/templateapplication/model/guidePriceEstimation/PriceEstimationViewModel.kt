package com.example.templateapplication.model.guidePriceEstimation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.templateapplication.api.RestApiApplication
import com.example.templateapplication.data.ApiRepository
import com.example.templateapplication.data.GoogleMapsRepository
import com.example.templateapplication.model.adres.ApiResponse
import com.example.templateapplication.model.quotationRequest.GoogleMapsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Calendar

class PriceEstimationViewModel(private val restApiRepository: ApiRepository,
                               private val googleMapsRepository: GoogleMapsRepository
) :
    ViewModel() {

    private val _estimationDetailsState = MutableStateFlow(EstimationUiState())
    val estimationDetailsState = _estimationDetailsState.asStateFlow()

    init {
        getApiEstimationDetails()
    }

    var priceEstimationApiState: PriceEstimationDetailsApiState by mutableStateOf(
        PriceEstimationDetailsApiState.Loading
    )
        private set

    private fun getApiEstimationDetails() {
        viewModelScope.launch {
            try {
                val result = restApiRepository.getEstimationDetails()
                _estimationDetailsState.update {
                    it.copy(
                        dbData = EstimationDetails(
                            formulas = result.formulas,
                            equipment = result.equipment,
                            unavailableDates = result.unavailableDates,
                        )
                    )
                }
                priceEstimationApiState = PriceEstimationDetailsApiState.Success(result)
            } catch (e: IOException) {
                val errorMessage = e.message ?: "An error occurred"
                priceEstimationApiState =
                    PriceEstimationDetailsApiState.Error(errorMessage) // TODO use this in ui
            }

        }
    }

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

    fun selectFormula(id: Int) {
        _estimationDetailsState.update {
            it.copy(selectedFormula = id)
        }
    }

    fun setAmountOfPeople(amountOfPeople: String) {
        if (amountOfPeople.isDigitsOnly()) {
            _estimationDetailsState.update {
                it.copy(amountOfPeople = amountOfPeople)
            }
        }
    }

    fun setWantsTripelBeer(wantsTripelBeer: Boolean) {
        _estimationDetailsState.update {
            it.copy(wantsTripelBeer = wantsTripelBeer)
        }
    }

    fun setWantsExtras(wantsExtras: Boolean) {
        _estimationDetailsState.update {
            it.copy(wantsExtras = wantsExtras)
        }
    }

    private var selectedExtras = listOf<EstimationEquipment>().toMutableStateList()

    fun extraItemsOnCheckedChange(extraItem: EstimationEquipment) {
        if (selectedExtras.contains(extraItem))
            selectedExtras.remove(extraItem)
        else
            selectedExtras.add(extraItem)
    }

    fun hasSelectedExtraItem(extraItem: EstimationEquipment): Boolean {
        return selectedExtras.contains(extraItem)
    }

    fun setDropDownExpanded(value: Boolean) {
        _estimationDetailsState.update {
            it.copy(formulaDropDownIsExpanded = value)
        }
    }

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

    fun placeFound(): Boolean {
        return if (_estimationDetailsState.value.placeResponse.candidates.isNotEmpty())
            _estimationDetailsState.value.placeResponse.candidates[0].formatted_address.isNotEmpty()
        else false
    }

}