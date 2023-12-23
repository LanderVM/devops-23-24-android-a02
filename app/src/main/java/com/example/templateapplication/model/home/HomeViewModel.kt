package com.example.templateapplication.model.home

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException

class HomeViewModel(
    private val restApiRepository: ApiRepository,
) : ViewModel() {
    private val _homeUiState = MutableStateFlow(HomeScreenUiState())
    val homeUiState = _homeUiState.asStateFlow()

    private var apiState: FormulaApiState by mutableStateOf(FormulaApiState.Loading)

    init {
        getFormulas()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RestApiApplication)
                val apiRepository =
                    application.container.apiRepository
                HomeViewModel(
                    restApiRepository = apiRepository,
                )
            }
        }
    }


    lateinit var formulaList: StateFlow<FormulaListState>

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
        } catch (e: SocketTimeoutException) {
            // device is offline, use roomdb instead
        }
    }

    fun updateOpenDialog(openDialog: Boolean) {
        _homeUiState.update {
            it.copy(openDialog = openDialog)
        }
    }
}