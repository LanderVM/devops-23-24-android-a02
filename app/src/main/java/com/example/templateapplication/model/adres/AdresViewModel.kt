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
    val btwNummer: String
        get() = adresUiState.value.btwNummer


    init {

    }

    fun allFieldsFilled(): Boolean {
        val buttonEnabled:Boolean
        buttonEnabled = !(straat.isBlank()||straat.isEmpty() || huisnummer.isBlank()||huisnummer.isEmpty() ||
                gemeente.isBlank()||gemeente.isEmpty() || postcode.isBlank()||postcode.isEmpty())

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


    fun updateBtwNummer(btwNummer: String){
        _adresUiState.update {
            it.copy(btwNummer = btwNummer)
        }
    }

}