package com.example.templateapplication.model.adres

//TODO val van maken
data class AdresUiState(
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