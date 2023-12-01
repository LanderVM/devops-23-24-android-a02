package com.example.templateapplication.model.quotationRequest

data class QuotationUiState (
    val dropDownExpanded: Boolean = false,
    val hasError: Boolean = false,
    val dateError: String? = null, // TODO
    val eventAddressError: String = "",
)