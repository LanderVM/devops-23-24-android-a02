package com.example.templateapplication.model.guidePriceEstimation

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
}