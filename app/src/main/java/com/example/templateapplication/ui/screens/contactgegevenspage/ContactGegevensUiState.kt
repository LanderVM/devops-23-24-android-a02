package com.example.templateapplication.ui.screens.contactgegevenspage

data class ContactGegevensUiState (
    val naam:String = "",
    val voornaam:String = "",
    val typeEvenement:String = "",
    val email:String = "",

    val straat:String = "",
    val huisnummer:String = "",
    val gemeente:String = "",
    val postcode:String = "",

    val facturatieAdressChecked:Boolean = true,

    val straatFacturatie:String = "",
    val huisnummerFacturatie:String = "",
    val gemeenteFacturatie:String = "",
    val postcodeFacturatie:String = ""
){

}