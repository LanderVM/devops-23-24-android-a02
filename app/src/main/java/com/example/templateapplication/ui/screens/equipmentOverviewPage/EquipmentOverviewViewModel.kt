package com.example.templateapplication.ui.screens.equipmentOverviewPage

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
import com.example.templateapplication.model.common.quotation.Equipment
import com.example.templateapplication.model.extraMateriaal.EquipmentListState
import com.example.templateapplication.model.extraMateriaal.EquipmentApiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * ViewModel for managing and displaying equipment overview.
 *
 * @property restApiRepository An instance of ApiRepository used for fetching and updating equipment data.
 */
class EquipmentOverviewViewModel(
    private val restApiRepository: ApiRepository,
) : ViewModel() {

    // Variable to track the current state of equipment API call.
    var extraMateriaalApiState: EquipmentApiState by mutableStateOf(EquipmentApiState.Loading)
        private set

    // Initialize equipment data on ViewModel creation.
    init {
        getApiExtraEquipment()
    }

    companion object {
        // Factory for creating instances of EquipmentOverviewViewModel.
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RestApiApplication)
                val apiRepository =
                    application.container.apiRepository
                EquipmentOverviewViewModel(
                    restApiRepository = apiRepository,
                )
            }
        }
    }

    // StateFlow for managing the list of equipment.
    lateinit var equipmentDbList: StateFlow<EquipmentListState>

    /**
     * Fetches extra equipment data from the API and updates the equipment list state.
     * Sets the extraMateriaalApiState based on the result of the fetch operation.
     */
    private fun getApiExtraEquipment() {
        try {
            viewModelScope.launch { restApiRepository.refresh() }
            equipmentDbList = restApiRepository.getEquipment().map { EquipmentListState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = EquipmentListState(),
                )

            extraMateriaalApiState = EquipmentApiState.Success
        } catch (e: IOException) {
            val errorMessage = e.message ?: "An error occurred"
            extraMateriaalApiState =
                EquipmentApiState.Error(errorMessage)
        }
    }

    /**
     * Returns a sorted list of equipment based on the specified index.
     *
     * @param index Int value representing the sorting criteria.
     * @return List of sorted Equipment objects.
     * @throws IllegalArgumentException if the index is invalid.
     */
    fun getListSorted(index: Int): List<Equipment> {
        Log.i("Test", equipmentDbList.value.toString())
        val sortedList = when (index) {
            0 -> equipmentDbList.value.equipmentListState.sortedByDescending { it.price } // Sort by price descending
            1 -> equipmentDbList.value.equipmentListState.sortedBy { it.price } // Sort by price ascending
            2 -> equipmentDbList.value.equipmentListState.sortedBy { it.title } // Sort by title ascending
            3 -> equipmentDbList.value.equipmentListState.sortedByDescending { it.title } // Sort by title descending
            else -> throw IllegalArgumentException("Invalid index: $index")
        }
        return sortedList
    }
}

