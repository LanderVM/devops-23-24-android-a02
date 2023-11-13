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

    private val _uiStatePlace = MutableStateFlow(GoogleMapsPlaceState(GooglePlaceResponse(arrayListOf())))
    val uiStatePlace: StateFlow<GoogleMapsPlaceState> = _uiStatePlace.asStateFlow()


    var googleMapsPredictionApiState: ApiResponse<GoogleMapsPredictionsState> by mutableStateOf(ApiResponse.Loading)
        private set

    init {
    }

    fun updateInput(input: String) {
        _uiStatePrediciton.update {
            it.copy(input = input)
        }
        //getPredictions(input)
    }

    fun updateMarker(marker: LatLng) {
        _uiStatePrediciton.update {
            it.copy(marker = marker)
        }
    }

    private fun getPredictions(input: String){
        viewModelScope.launch {
            try{
                val listResult = tasksRepository.getPredictions(input = input)
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