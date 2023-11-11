package com.example.templateapplication.model.adres

//TODO rename to viewmodel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AdresViewModel : ViewModel() {
    private val _adresUiState = MutableStateFlow(AdresUiState())
    val adresUiState: StateFlow<AdresUiState> = _adresUiState.asStateFlow()

    val straat: String
        get() = adresUiState.value.straat

    val gemeente: String
        get() = adresUiState.value.gemeente

    val postcode: String
        get() = adresUiState.value.postcode

    val huisnummer: String
        get() = adresUiState.value.huisnummer

    val facturatieAdressChecked: Boolean
        get() = adresUiState.value.facturatieAdressChecked

    val postcodeFacturatie: String
        get() = adresUiState.value.postcodeFacturatie

    val gemeenteFacturatie: String
        get() = adresUiState.value.gemeenteFacturatie

    val huisnummerFacturatie: String
        get() = adresUiState.value.huisnummerFacturatie

    val straatFacturatie: String
        get() = adresUiState.value.straatFacturatie

    init {

    }

    fun allFieldsFilled(): Boolean {
        val buttonEnabled:Boolean
        buttonEnabled = !(straat.isBlank()||straat.isEmpty() || huisnummer.isBlank()||huisnummer.isEmpty() ||
                gemeente.isBlank()||gemeente.isEmpty() || postcode.isBlank()||postcode.isEmpty() ||
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

    fun updateStraat(straat: String) {
        _adresUiState.update {
            it.copy(straat = straat)
        }
    }

    fun updateHuisnummer(huisnummer: String) {
        _adresUiState.update {
            it.copy(huisnummer = huisnummer)
        }
    }

    fun updateGemeente(gemeente: String) {
        _adresUiState.update {
            it.copy(gemeente = gemeente)
        }
    }

    fun updatePostcode(postcode: String) {
        _adresUiState.update {
            it.copy(postcode = postcode)
        }
    }

    fun updateFacturatieAdressChecked(checked: Boolean) {
        _adresUiState.update {
            it.copy(facturatieAdressChecked = checked)
        }
    }

    fun updateStraatFacturatie(straatFacturatie: String) {
        _adresUiState.update {
            it.copy(straatFacturatie = straatFacturatie)
        }
    }

    fun updateHuisnummerFacturatie(huisnummerFacturatie: String) {
        _adresUiState.update {
            it.copy(huisnummerFacturatie = huisnummerFacturatie)
        }
    }

    fun updateGemeenteFacturatie(gemeenteFacturatie: String) {
        _adresUiState.update {
            it.copy(gemeenteFacturatie = gemeenteFacturatie)
        }
    }

    fun updatePostcodeFacturatie(postcodeFacturatie: String) {
        _adresUiState.update {
            it.copy(postcodeFacturatie = postcodeFacturatie)
        }
    }

}