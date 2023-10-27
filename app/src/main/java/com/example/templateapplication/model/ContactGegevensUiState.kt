package com.example.templateapplication.model

data class ContactGegevensUiState(
    var naam: String = "",
    var voornaam: String = "",
    var typeEvenement: String = "",
    var email: String = "",
    var straat: String = "",
    var huisnummer: String = "",
    var gemeente: String = "",
    var postcode: String = "",
    var facturatieAdressChecked: Boolean = true,
    var straatFacturatie: String = "",
    var huisnummerFacturatie: String = "",
    var gemeenteFacturatie: String = "",
    var postcodeFacturatie: String = "",
)
