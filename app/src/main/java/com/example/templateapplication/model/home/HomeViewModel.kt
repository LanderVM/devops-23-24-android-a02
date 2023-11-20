package com.example.templateapplication.model.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalTime

class HomeViewModel : ViewModel() {
    private val _homeUiState = MutableStateFlow(HomeScreenUiState())
    val homeUiState = _homeUiState.asStateFlow()


    fun updateOpenDialog(openDialog: Boolean) {
        _homeUiState.update {
            it.copy(openDialog = openDialog)
        }
    }
}