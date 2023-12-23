package com.example.templateapplication.ui.screens.formulaDetails

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
import com.example.templateapplication.model.common.quotation.FormulaApiState
import com.example.templateapplication.model.common.quotation.FormulaListState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * ViewModel for managing and displaying formulas.
 *
 * This ViewModel handles the fetching and presentation of formulas using data from a REST API.
 * It maintains the state of the API call and provides the fetched data to the UI.
 *
 * @property restApiRepository An instance of ApiRepository used for making API calls.
 */
class FormulasViewModel(
    private val restApiRepository: ApiRepository,
) : ViewModel() {

    /**
     * State variable to track the current status of the formula fetch API call.
     */
    private var apiState: FormulaApiState by mutableStateOf(FormulaApiState.Loading)

    init {
        getFormulas()
    }

    /**
     * Factory for creating instances of [FormulasViewModel].
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RestApiApplication)
                val apiRepository =
                    application.container.apiRepository
                FormulasViewModel(
                    restApiRepository = apiRepository,
                )
            }
        }
    }

    /**
     * A [StateFlow] that manages and exposes the list of formulas.
     */
    lateinit var formulaList: StateFlow<FormulaListState>

    /**
     * Fetches formulas from the API and updates the state of [formulaList].
     * It sets [apiState] based on the result of the fetch operation.
     */
    private fun getFormulas() {
        try {
            viewModelScope.launch { restApiRepository.refresh() }
            formulaList = restApiRepository.getFormulas().map { FormulaListState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = FormulaListState(),
                )

            apiState = FormulaApiState.Success
        } catch (e: IOException) {
            val errorMessage = e.message ?: "An error occurred"
            apiState =
                FormulaApiState.Error(errorMessage)
        }
    }
}