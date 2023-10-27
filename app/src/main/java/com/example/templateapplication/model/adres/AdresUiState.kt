package com.example.templateapplication.model.adres

data class AdresUiState(
    var straat: String = "",
    val huisNummer: String = "",
    val gemeente: String = "",
    val postcode: String = ""
)