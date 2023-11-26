package com.example.templateapplication.model.guidePriceEstimation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.templateapplication.api.GuidePriceEstimationApplication
import com.example.templateapplication.data.GuidePriceEstimationRepository
import com.example.templateapplication.network.guidePriceEstimation.EstimationDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class PriceEstimationViewModel(private val priceEstimationRepository: GuidePriceEstimationRepository) :
    ViewModel() {

    private val _extraItemListState = MutableStateFlow(EstimationDetails(null, null, null))
    val extraItemListState = _extraItemListState.asStateFlow()
    private fun getApiEstimationDetails() {
        viewModelScope.launch {
            try {
                val result = priceEstimationRepository.getEstimationDetails()
                _extraItemListState.update {
                    it.copy(formulas = result.formulas, equipment = result.equipment, unavailableDays = result.unavailableDays)
                }
                priceEstimationApiState = PriceEstimationDetailsApiState.Success(result)
            } catch (e: IOException) {
                val errorMessage = e.message ?: "An error occurred"
                priceEstimationApiState = PriceEstimationDetailsApiState.Error(errorMessage)
            }

        }
    }

    var priceEstimationApiState: PriceEstimationDetailsApiState by mutableStateOf(PriceEstimationDetailsApiState.Loading)
        private set

    init {
        getApiEstimationDetails()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GuidePriceEstimationApplication)
                val guidePriceEstimationRepository = application.container.guidePriceEstimationRepository
                PriceEstimationViewModel(
                    priceEstimationRepository = guidePriceEstimationRepository
                )
            }
        }
    }
}