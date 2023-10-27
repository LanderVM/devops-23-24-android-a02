package com.example.templateapplication.model.adres

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AdresViewModel : ViewModel() {
    private val _adresUiState = MutableStateFlow(AdresUiState())
    val uiState: StateFlow<AdresUiState> = _adresUiState.asStateFlow()

    var userGuess by mutableStateOf("")
        private set

    private var usedWords: MutableSet<String> = mutableSetOf()
    private lateinit var currentWord: String

    init {

    }

    fun validatieAdres(gemeente: String, straat: String): Boolean {
        _adresUiState.value.straat = straat
        if (gemeente == "Opwijk")
            return true
        return false
    }
}