package com.example.templateapplication.model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.templateapplication.model.klant.GegevensUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    val typeEvenement: String
        get() = gegevensUiState.value.typeEvenement

    val straat: String
        get() = gegevensUiState.value.straat

    val gemeente: String
        get() = gegevensUiState.value.gemeente

    val postcode: String
        get() = gegevensUiState.value.postcode

    val huisnummer: String
        get() = gegevensUiState.value.huisnummer

    val facturatieAdressChecked: Boolean
        get() = gegevensUiState.value.facturatieAdressChecked

    val postcodeFacturatie: String
        get() = gegevensUiState.value.postcodeFacturatie

    val gemeenteFacturatie: String
        get() = gegevensUiState.value.gemeenteFacturatie

    val huisnummerFacturatie: String
        get() = gegevensUiState.value.huisnummerFacturatie

    val straatFacturatie: String
        get() = gegevensUiState.value.straatFacturatie

    init {

    }

    fun allFieldsFilled(): Boolean {
        val buttonEnabled:Boolean
        buttonEnabled = !(naam.isBlank()||naam.isEmpty()||voornaam.isBlank()||voornaam.isEmpty()||typeEvenement.isBlank()||
                typeEvenement.isEmpty()||email.isBlank()||email.isEmpty()||
                straat.isBlank()||straat.isEmpty()||huisnummer.isBlank()||huisnummer.isEmpty()||
                gemeente.isBlank()||gemeente.isEmpty()||postcode.isBlank()||postcode.isEmpty()||
                (!facturatieAdressChecked&&straatFacturatie.isEmpty())||(!facturatieAdressChecked&&straatFacturatie.isBlank())
                ||
                (!facturatieAdressChecked&&gemeenteFacturatie.isEmpty())||(!facturatieAdressChecked&&gemeenteFacturatie.isBlank())
                ||
                (!facturatieAdressChecked&&huisnummerFacturatie.isEmpty())||(!facturatieAdressChecked&&huisnummerFacturatie.isBlank())
                ||
                (!facturatieAdressChecked&&postcodeFacturatie.isEmpty())||(!facturatieAdressChecked&&postcodeFacturatie.isBlank())
                )
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

    fun updateTypeEvenement(typeEvenement: String) {
        _gegevensUiState.update {
            it.copy(typeEvenement = typeEvenement)
        }
    }

    fun updateEmail(email: String) {
        _gegevensUiState.update {
            it.copy(email = email)
        }
    }

    fun updateStraat(straat: String) {
        _gegevensUiState.update {
            it.copy(straat = straat)
        }
    }

    fun updateHuisnummer(huisnummer: String) {
        _gegevensUiState.update {
            it.copy(huisnummer = huisnummer)
        }
    }

    fun updateGemeente(gemeente: String) {
        _gegevensUiState.update {
            it.copy(gemeente = gemeente)
        }
    }

    fun updatePostcode(postcode: String) {
        _gegevensUiState.update {
            it.copy(postcode = postcode)
        }
    }

    fun updateFacturatieAdressChecked(checked: Boolean) {
        _gegevensUiState.update {
            it.copy(facturatieAdressChecked = checked)
        }
    }

    fun updateStraatFacturatie(straatFacturatie: String) {
        _gegevensUiState.update {
            it.copy(straatFacturatie = straatFacturatie)
        }
    }

    fun updateHuisnummerFacturatie(huisnummerFacturatie: String) {
        _gegevensUiState.update {
            it.copy(huisnummerFacturatie = huisnummerFacturatie)
        }
    }

    fun updateGemeenteFacturatie(gemeenteFacturatie: String) {
        _gegevensUiState.update {
            it.copy(gemeenteFacturatie = gemeenteFacturatie)
        }
    }

    fun updatePostcodeFacturatie(postcodeFacturatie: String) {
        _gegevensUiState.update {
            it.copy(postcodeFacturatie = postcodeFacturatie)
        }
    }
}