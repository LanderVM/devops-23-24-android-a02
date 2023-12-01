package com.example.templateapplication.ui.screens

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
import com.example.templateapplication.model.adres.ApiResponse
import com.example.templateapplication.model.adres.GoogleMapsDistanceState
import com.example.templateapplication.model.adres.GoogleMapsPlaceState
import com.example.templateapplication.model.adres.GoogleMapsPredictionsState
import com.example.templateapplication.model.common.googleMaps.GoogleMapsDistance
import com.example.templateapplication.model.common.googleMaps.GoogleMapsPlace
import com.example.templateapplication.model.common.googleMaps.GoogleMapsPrediction
import com.example.templateapplication.model.quotationRequest.QuotationRequestState
import com.example.templateapplication.model.quotationRequest.QuotationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class QuotationViewModel(
    private val restApiRepository: ApiRepository,
    private val tasksRepository: GoogleMapsRepository
) :
    ViewModel() {


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RestApiApplication)
                val tasksRepository = application.container.googleMapsRepository
                val guidePriceEstimationRepository =
                    application.container.apiRepository
                QuotationViewModel(
                    restApiRepository = guidePriceEstimationRepository,
                    tasksRepository = tasksRepository
                )
            }
        }
    }

    // --------------------------------------------------------------------------------------------------------------
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

    fun canNavigateNext(): Boolean {
//        return !quotationUiState.value.hasError
//        return _quotationRequestState.value.amountOfPeople.isDigitsOnly()
//                && _quotationRequestState.value.amountOfPeople.isNotBlank()
//                && _quotationRequestState.value.amountOfPeople >
//        }
        return _quotationUiState.value.hasError
    }

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

    // -------------------------------------------------------------------------------------------------------------- GOOGLE MAPS

    private val _uiStatePrediction = MutableStateFlow(
        GoogleMapsPredictionsState(
            GoogleMapsPrediction(arrayListOf())
        )
    )
    val uiStatePrediction: StateFlow<GoogleMapsPredictionsState> = _uiStatePrediction.asStateFlow()

    var googleMapsPredictionApiState: ApiResponse<GoogleMapsPredictionsState> by mutableStateOf(
        ApiResponse.Loading
    )
        private set

    // Plaats
    private val _uiStatePlace =
        MutableStateFlow(GoogleMapsPlaceState(GoogleMapsPlace(arrayListOf())))
    val uiStatePlace: StateFlow<GoogleMapsPlaceState> = _uiStatePlace.asStateFlow()

    var googleMapsPlaceApiState: ApiResponse<GoogleMapsPlaceState> by mutableStateOf(ApiResponse.Loading)
        private set

    // Afstand
    private val _uiStateDistance =
        MutableStateFlow(GoogleMapsDistanceState(GoogleMapsDistance(arrayListOf())))
    val uiStateDistance: StateFlow<GoogleMapsDistanceState> = _uiStateDistance.asStateFlow()

    var googleMapsDistanceApiState: ApiResponse<GoogleMapsDistanceState> by mutableStateOf(
        ApiResponse.Loading
    )
        private set

    fun updateInput(input: String) {
        _uiStatePrediction.update {
            it.copy(input = input)
        }
    }

    fun getPredictions() {
        viewModelScope.launch {
            try {
                val listResult =
                    tasksRepository.getPredictions(input = _uiStatePrediction.value.input)
                _uiStatePrediction.update {
                    it.copy(predictionsResponse = listResult)
                }
                googleMapsPredictionApiState = ApiResponse.Success(
                    GoogleMapsPredictionsState(listResult)
                )
            } catch (e: IOException) {
                googleMapsPredictionApiState = ApiResponse.Error
                Log.i("Error", e.toString())
            }
        }
    }

    private fun updateDistance() {
        if (placeFound()) {
            viewModelScope.launch {
                try {
                    val distanceResult = tasksRepository.getDistance(
                        vertrekPlaats = "${_uiStatePlace.value.marker.latitude}, ${_uiStatePlace.value.marker.longitude}",
                        eventPlaats = _uiStatePlace.value.placeResponse.candidates[0].formatted_address
                    )
                    _uiStateDistance.update {
                        it.copy(distanceResponse = distanceResult)
                    }
                    googleMapsDistanceApiState = ApiResponse.Success(
                        GoogleMapsDistanceState(distanceResult)
                    )
                } catch (e: IOException) {
                    googleMapsDistanceApiState = ApiResponse.Error
                    Log.i("Error", e.toString())
                }
            }
        }
    }

    fun updateMarker() {
        viewModelScope.launch {
            try {
                val placeResult = tasksRepository.getPlace(input = _uiStatePrediction.value.input)
                _uiStatePlace.update {
                    it.copy(placeResponse = placeResult)
                }
                googleMapsPlaceApiState = ApiResponse.Success(
                    GoogleMapsPlaceState(placeResult)
                )
                updateDistance()
            } catch (e: IOException) {
                googleMapsPlaceApiState = ApiResponse.Error
                Log.i("Error", e.toString())
            }
        }
    }

    fun getDistanceLong(): Long? {
        if (uiStateDistance.value.distanceResponse.rows.isEmpty() or uiStatePrediction.value.input.isBlank()) {
            return null
        }
        return uiStateDistance.value.distanceResponse.rows[0].elements[0].distance.value
    }

    fun getDistanceString(): String {
        if (uiStateDistance.value.distanceResponse.rows.isEmpty() or uiStatePrediction.value.input.isBlank()) {
            return ""
        }
        return "Afstand: " + uiStateDistance.value.distanceResponse.rows[0].elements[0].distance.text
    }

    fun placeFound(): Boolean {
        return if (uiStatePlace.value.placeResponse.candidates.size != 0)
            uiStatePlace.value.placeResponse.candidates[0].formatted_address.isNotEmpty()
        else false
    }

}