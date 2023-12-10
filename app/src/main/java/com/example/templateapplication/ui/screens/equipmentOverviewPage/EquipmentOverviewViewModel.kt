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
import com.example.templateapplication.model.extraMateriaal.ExtraItemDetailsApiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException

class EquipmentOverviewViewModel(
    private val restApiRepository: ApiRepository,
) :
    ViewModel() {

    init {
        getApiExtraEquipment()
    }

    companion object {
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

    var extraMateriaalApiState: ExtraItemDetailsApiState by mutableStateOf(ExtraItemDetailsApiState.Loading)
        private set

    lateinit var equipmentDbList: StateFlow<List<Equipment>>

    private fun getApiExtraEquipment() {
        try {
            viewModelScope.launch { restApiRepository.refresh() }
            equipmentDbList = restApiRepository.getEquipment()
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = listOf(),
                )

        //            extraMateriaalApiState = ExtraItemDetailsApiState.Success FIXME null reference als dit geset wordt?... pls someone fix this I don't get it
        } catch (e: IOException) {
            val errorMessage = e.message ?: "An error occurred"
            extraMateriaalApiState = ExtraItemDetailsApiState.Error(errorMessage) // hier waarschijnlijk ook dan?
        }
    }


    fun getListSorted(index: Int): List<Equipment> {
        Log.i("Test", equipmentDbList.value.toString())
        val sortedList = when (index) {
            0 -> equipmentDbList.value.sortedBy { it.price } // Sort asc
            1 -> equipmentDbList.value.sortedByDescending { it.price } // Sort desc
            2 -> equipmentDbList.value.sortedBy { it.title } // Sort by name asc
            3 -> equipmentDbList.value.sortedByDescending { it.title } // Sort by name desc
            else -> throw IllegalArgumentException("Invalid index: $index")
        }
        return sortedList
    }
}
