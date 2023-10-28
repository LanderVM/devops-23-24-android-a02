package com.example.templateapplication.ui.screens.contactgegevenspage

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ContactGegevensViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(ContactGegevensUiState())
    val uiState: StateFlow<ContactGegevensUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = ContactGegevensUiState()
    }

    fun updateNaam(newNaam:String) {
        _uiState.update { currentState ->
            currentState.copy(
                naam = newNaam
            )

        }
    }
    fun updateVoornaam(newVoornaam:String) {
        _uiState.update { currentState ->
            currentState.copy(
                voornaam = newVoornaam
            )

        }
    }
    fun updateTypeEvenement(newTypeEvenement:String) {
        _uiState.update { currentState ->
            currentState.copy(
                typeEvenement = newTypeEvenement
            )

        }
    }
    fun updateEmail (newEmail:String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = newEmail
            )

        }
    }

    fun updateStraat(newStraat:String) {
        _uiState.update { currentState ->
            currentState.copy(
                straat = newStraat
            )

        }
    }
    fun updateHuisnummer(newHuisnummer:String) {
        _uiState.update { currentState ->
            currentState.copy(
                huisnummer = newHuisnummer
            )

        }
    }
    fun updateGemeente(newGemeente:String) {
        _uiState.update { currentState ->
            currentState.copy(
                gemeente = newGemeente
            )

        }
    }
    fun updatePostcode (newPostcode:String) {
        _uiState.update { currentState ->
            currentState.copy(
                postcode = newPostcode
            )

        }
    }

    fun updateFacturatieAdressChecked (newChecked:Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                facturatieAdressChecked = newChecked
            )

        }
    }

    fun updateStraatFacturatie(newStraat:String) {
        _uiState.update { currentState ->
            currentState.copy(
                straatFacturatie = newStraat
            )

        }
    }
    fun updateHuisnummerFacturatie(newHuisnummer:String) {
        _uiState.update { currentState ->
            currentState.copy(
                huisnummerFacturatie = newHuisnummer
            )

        }
    }
    fun updateGemeenteFacturatie(newGemeente:String) {
        _uiState.update { currentState ->
            currentState.copy(
                gemeenteFacturatie = newGemeente
            )

        }
    }
    fun updatePostcodeFacturatie (newPostcode:String) {
        _uiState.update { currentState ->
            currentState.copy(
                postcodeFacturatie = newPostcode
            )

        }
    }
}