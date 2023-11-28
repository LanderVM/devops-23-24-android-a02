package com.example.templateapplication.model.formules

import java.util.Calendar

data class FormuleUiState(
    val startDate: Calendar? = null,
    val endDate: Calendar? = null,
    val selectedFormula: Int = -1,
    val wantsTripelBeer: Boolean = false,
    val dropDownExpanded: Boolean = false,
)