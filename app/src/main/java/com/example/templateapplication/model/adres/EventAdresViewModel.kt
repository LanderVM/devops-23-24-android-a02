package com.example.templateapplication.model.adres

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.templateapplication.api.GoogleMapsApplication
import com.example.templateapplication.data.GoogleMapsRepository
import com.example.templateapplication.network.GoogleDistanceResponse
import com.example.templateapplication.network.GooglePlaceResponse
import com.example.templateapplication.network.GooglePredictionsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class EventAdresViewModel(private val tasksRepository: GoogleMapsRepository) : ViewModel() {
    // Autocomplete
    private val _uiStatePrediciton = MutableStateFlow(GoogleMapsPredictionsState(GooglePredictionsResponse(arrayListOf())))
    val uiStatePrediction: StateFlow<GoogleMapsPredictionsState> = _uiStatePrediciton.asStateFlow()

    var googleMapsPredictionApiState: ApiResponse<GoogleMapsPredictionsState> by mutableStateOf(ApiResponse.Loading)
        private set

    // Plaats
    private val _uiStatePlace = MutableStateFlow(GoogleMapsPlaceState(GooglePlaceResponse(arrayListOf())))
    val uiStatePlace: StateFlow<GoogleMapsPlaceState> = _uiStatePlace.asStateFlow()

    var googleMapsPlaceApiState: ApiResponse<GoogleMapsPlaceState> by mutableStateOf(ApiResponse.Loading)
        private set

    // Afstand
    private val _uiStateDistance = MutableStateFlow(GoogleMapsDistanceState(GoogleDistanceResponse(arrayListOf())))
    val uiStateDistance: StateFlow<GoogleMapsDistanceState> = _uiStateDistance.asStateFlow()

    var googleMapsDistanceApiState: ApiResponse<GoogleMapsDistanceState> by mutableStateOf(ApiResponse.Loading)
        private set

    init {
    }

    fun updateInput(input: String) {
        _uiStatePrediciton.update {
            it.copy(input = input)
        }
    }

    fun getPredictions(){
        viewModelScope.launch {
            try{
                val listResult = tasksRepository.getPredictions(input = _uiStatePrediciton.value.input)
                _uiStatePrediciton.update {
                    it.copy(predictionsResponse = listResult)
                }
                googleMapsPredictionApiState = ApiResponse.Success(
                    GoogleMapsPredictionsState(listResult)
                )
            }
            catch (e: IOException){
                googleMapsPredictionApiState = ApiResponse.Error
                Log.i("Error", e.toString())
            }
        }
    }

    fun updateDistance() {
        if (checkForPlace()) {
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
                } catch (e: IOException){
                    googleMapsDistanceApiState = ApiResponse.Error
                    Log.i("Error", e.toString())
                }
            }
        }
    }

    fun updateMarker() {
        viewModelScope.launch {
            try {
                val placeResult = tasksRepository.getPlace(input = _uiStatePrediciton.value.input)
                _uiStatePlace.update {
                    it.copy(placeResponse = placeResult)
                }
                googleMapsPlaceApiState = ApiResponse.Success(
                    GoogleMapsPlaceState(placeResult)
                )
                updateDistance()
            } catch (e: IOException){
                googleMapsPlaceApiState = ApiResponse.Error
                Log.i("Error", e.toString())
            }
        }
    }

    fun getDistanceLong() : Long? {
        if (uiStateDistance.value.distanceResponse.rows.isEmpty() or uiStatePrediction.value.input.isBlank()) {
            return null
        }
        return uiStateDistance.value.distanceResponse.rows[0].elements[0].distance.value
    }

    fun getDistanceString() : String {
        if (uiStateDistance.value.distanceResponse.rows.isEmpty() or uiStatePrediction.value.input.isBlank()) {
            return ""
        }
        return "Afstand: " + uiStateDistance.value.distanceResponse.rows[0].elements[0].distance.text
    }

    // Check of er een plaats is gevonden met de autocomplete
    fun checkForPlace() : Boolean {
        if (uiStatePlace.value.placeResponse.candidates.isNotEmpty()) {
            return true
        }
        return false
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as GoogleMapsApplication)
                val tasksRepository = application.container.googleMapsRepository
                EventAdresViewModel(tasksRepository = tasksRepository
                )
            }
        }
    }
}