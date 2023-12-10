package com.example.templateapplication.data

import androidx.compose.ui.graphics.Color
import com.example.templateapplication.model.common.quotation.Formula
import com.example.templateapplication.ui.theme.MainColor
import com.example.templateapplication.ui.theme.MainDarkColor
import com.example.templateapplication.ui.theme.MainLightColor
import com.example.templateapplication.ui.theme.MainLighterColor
import com.example.templateapplication.ui.theme.MainLightestColor

//TODO interface van maken
class Datasource() {
    fun loadFormules(): List<Formula> {
        val basis = listOf("Foodtruck", "Optioneel bier")
        val formulaBasis = Formula(0, "Basis", basis)

        val allIn = listOf("Foodtruck", "Vat(en)", "Glazen")
        val formulaAllIn = Formula(1, "All-In", allIn)

        val gevordered = listOf("Foodtruck", "Vat(en)", "Glazen", "BBQ")
        val formulaGevordered = Formula(2, "Gevordered", gevordered)

        return listOf<Formula>(
            formulaBasis, formulaAllIn, formulaGevordered
        )
    }

    fun loadColors(): List<Color> {
        return listOf<Color>(
            MainColor,
            MainLightColor,
            MainLighterColor,
            MainLightestColor,
            MainDarkColor
        )
    }


}