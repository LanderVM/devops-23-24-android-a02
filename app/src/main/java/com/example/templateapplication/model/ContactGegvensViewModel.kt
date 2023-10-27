package com.example.templateapplication.model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ContactGegevensViewModel : ViewModel() {

    var naam by mutableStateOf("")
        private set
    var voornaam by mutableStateOf("")
        private set
    var typeEvenement by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var straat by mutableStateOf("")
        private set
    var huisnummer by mutableStateOf("")
        private set
    var gemeente by mutableStateOf("")
        private set
    var postcode by mutableStateOf("")
        private set
    var facturatieAdressChecked by mutableStateOf(true)
        private set
    var straatFacturatie by mutableStateOf("")
        private set
    var huisnummerFacturatie by mutableStateOf("")
        private set
    var gemeenteFacturatie by mutableStateOf("")
        private set
    var postcodeFacturatie by mutableStateOf("")
        private set

    init {

    }

    fun navigationArguments(): String {
        return "naam=$naam&voornaam=$voornaam&typeEvenement=$typeEvenement" +
                "&email=$email&straat=$straat&huisnummer=$huisnummer" +
                "&gemeente=$gemeente&postcode=$postcode" +
                "&facturatieAdressChecked=$facturatieAdressChecked" +
                "&straatFacturatie=$straatFacturatie&huisnummerFacturatie=$huisnummerFacturatie" +
                "&gemeenteFacturatie=$gemeenteFacturatie&postcodeFacturatie=$postcodeFacturatie"
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
        this.naam = naam
    }

    fun updateVoornaam(voornaam: String) {
        this.voornaam = voornaam
    }

    fun updateTypeEvenement(typeEvenement: String) {
        this.typeEvenement = typeEvenement
    }

    fun updateEmail(email: String) {
        this.email = email
    }

    fun updateStraat(straat: String) {
        this.straat = straat
    }

    fun updateHuisnummer(huisnummer: String) {
        this.huisnummer = huisnummer
    }

    fun updateGemeente(gemeente: String) {
        this.gemeente = gemeente
    }

    fun updatePostcode(postcode: String) {
        this.postcode = postcode
    }

    fun updateFacturatieAdressChecked(checked: Boolean) {
        this.facturatieAdressChecked = checked
    }

    fun updateStraatFacturatie(straatFacturatie: String) {
        this.straatFacturatie = straatFacturatie
    }

    fun updateHuisnummerFacturatie(huisnummerFacturatie: String) {
        this.huisnummerFacturatie = huisnummerFacturatie
    }

    fun updateGemeenteFacturatie(gemeenteFacturatie: String) {
        this.gemeenteFacturatie = gemeenteFacturatie
    }

    fun updatePostcodeFacturatie(postcodeFacturatie: String) {
        this.postcodeFacturatie = postcodeFacturatie
    }


    // You can add more functions to handle data as needed
}