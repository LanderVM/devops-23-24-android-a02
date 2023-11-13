package com.example.templateapplication.model.adres


data class AdresUiState(
    val straat: String = "",
    val huisnummer: String = "",
    val gemeente: String = "",
    val postcode: String = "",
    val facturatieAdressChecked: Boolean = true,
    val straatFacturatie: String = "",
    val huisnummerFacturatie: String = "",
    val gemeenteFacturatie: String = "",
    val postcodeFacturatie: String = "",
)