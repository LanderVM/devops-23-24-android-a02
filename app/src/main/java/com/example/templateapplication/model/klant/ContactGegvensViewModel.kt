package com.example.templateapplication.model.klant

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ContactGegevensViewModel : ViewModel() {

    private val _gegevensUiState = MutableStateFlow(ContactGegevensUiState())
    val gegevensUiState = _gegevensUiState.asStateFlow()

    val naam: String
        get() = gegevensUiState.value.naam

    val voornaam: String
        get() = gegevensUiState.value.voornaam

    val email: String
        get() = gegevensUiState.value.email

    val telefoonnummer: String
        get() = gegevensUiState.value.telefoonnummer


    init {

    }

    fun allFieldsFilled(): Boolean {
        val buttonEnabled:Boolean
        buttonEnabled = !(naam.isBlank()||naam.isEmpty()||voornaam.isBlank()||voornaam.isEmpty()||telefoonnummer.isBlank()||
                telefoonnummer.isEmpty()||email.isBlank()||email.isEmpty())
        return buttonEnabled
    }

    fun updateNaam(naam: String) {
        _gegevensUiState.update {
            it.copy(naam = naam)
        }
    }

    fun updateVoornaam(voornaam: String) {
        _gegevensUiState.update {
            it.copy(voornaam = voornaam)
        }
    }

    fun updateTelefoonnummer(telefoonnummer: String) {
        _gegevensUiState.update {
            it.copy(telefoonnummer = telefoonnummer)
        }
    }

    fun updateEmail(email: String) {
        _gegevensUiState.update {
            it.copy(email = email)
        }
    }
}