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
import com.example.templateapplication.network.ApiResponse
import com.example.templateapplication.network.GoogleDistanceResponse
import com.example.templateapplication.network.GoogleMapsDistanceState
import com.example.templateapplication.network.GoogleMapsPlaceState
import com.example.templateapplication.network.GoogleMapsPredictionsState
import com.example.templateapplication.network.GooglePlaceResponse
import com.example.templateapplication.network.GooglePredictionsResponse
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class GoogleMapsViewModel(private val tasksRepository: GoogleMapsRepository) : ViewModel() {
    private val _uiStatePrediciton = MutableStateFlow(GoogleMapsPredictionsState(GooglePredictionsResponse(arrayListOf())))
    val uiStatePrediction: StateFlow<GoogleMapsPredictionsState> = _uiStatePrediciton.asStateFlow()

    var googleMapsPredictionApiState: ApiResponse<GoogleMapsPredictionsState> by mutableStateOf(ApiResponse.Loading)
        private set

    private val _uiStatePlace = MutableStateFlow(GoogleMapsPlaceState(GooglePlaceResponse(arrayListOf())))
    val uiStatePlace: StateFlow<GoogleMapsPlaceState> = _uiStatePlace.asStateFlow()

    var googleMapsPlaceApiState: ApiResponse<GoogleMapsPlaceState> by mutableStateOf(ApiResponse.Loading)
        private set

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
        //getPredictions(input)
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
        viewModelScope.launch {
            try {
                Log.i("TEST", LatLng(
                    _uiStatePlace.value.placeResponse.candidates[0].geometry.location.lat,
                    _uiStatePlace.value.placeResponse.candidates[0].geometry.location.lng
                ).toString() + _uiStatePlace.value.marker)
                val distanceResult = tasksRepository.getDistance(
                    vertrekPlaats = _uiStatePlace.value.marker,
                    eventPlaats = LatLng(
                        _uiStatePlace.value.placeResponse.candidates[0].geometry.location.lat,
                        _uiStatePlace.value.placeResponse.candidates[0].geometry.location.lng
                    )
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
            } catch (e: IOException){
                googleMapsPlaceApiState = ApiResponse.Error
                Log.i("Error", e.toString())
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as GoogleMapsApplication)
                val tasksRepository = application.container.googleMapsRepository
                GoogleMapsViewModel(tasksRepository = tasksRepository
                )
            }
        }
    }
}