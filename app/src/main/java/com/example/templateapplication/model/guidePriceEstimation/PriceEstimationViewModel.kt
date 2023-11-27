package com.example.templateapplication.model.guidePriceEstimation

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class PriceEstimationViewModel(private val restApiRepository: ApiRepository) :
    ViewModel() {

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
                        dbDetails = EstimationDetails(
                            formulas = result.formulas,
                            equipment = result.equipment,
                            unavailableDates = result.unavailableDates,
                        )
                    )
                }
                priceEstimationApiState = PriceEstimationDetailsApiState.Success(result)
            } catch (e: IOException) {
                val errorMessage = e.message ?: "An error occurred"
                priceEstimationApiState = PriceEstimationDetailsApiState.Error(errorMessage)
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
                PriceEstimationViewModel(
                    restApiRepository = guidePriceEstimationRepository
                )
            }
        }
    }

    private val _estimationDetailsState = MutableStateFlow(EstimationScreenState())
    val estimationDetailsState = _estimationDetailsState.asStateFlow()

    fun selectFormula(id: Int) {
        _estimationDetailsState.value.selectedFormula = id
    }
}