package com.example.templateapplication.data

import androidx.compose.ui.graphics.Color
import com.example.templateapplication.model.formules.Formule
import com.example.templateapplication.ui.theme.MainColor
import com.example.templateapplication.ui.theme.MainDarkColor
import com.example.templateapplication.ui.theme.MainLightColor
import com.example.templateapplication.ui.theme.MainLighterColor
import com.example.templateapplication.ui.theme.MainLightestColor

class Datasource() {
    fun loadFormules(): List<Formule> {
        val basis = listOf("Foodtruck", "Optioneel bier")
        val formuleBasis = Formule("Basis", basis)

        val allIn = listOf("Foodtruck", "Vat(en)", "Glazen")
        val formuleAllIn = Formule("All-In", allIn)

        val gevordered = listOf("Foodtruck", "Vat(en)", "Glazen", "BBQ")
        val formuleGevordered = Formule("Gevordered", gevordered)

        return listOf<Formule>(
            formuleBasis, formuleAllIn, formuleGevordered
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