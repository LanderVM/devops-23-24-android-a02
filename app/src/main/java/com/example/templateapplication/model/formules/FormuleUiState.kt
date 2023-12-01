package com.example.templateapplication.model.formules

import com.example.templateapplication.ui.commons.DropDownOption
import java.util.Calendar

data class FormuleUiState(
    val startDate: Calendar? = null,
    val endDate: Calendar? = null,
    val selectedFormula: Int = -1,
    val wantsTripelBeer: Boolean = false,
    val dropDownExpanded: Boolean = false,
    val beerDropDownOptions: List<DropDownOption> = listOf(
        DropDownOption("Pils", 0),
        DropDownOption("Tripel", 1)
    ),
    val amountOfPeople: String = "",
)