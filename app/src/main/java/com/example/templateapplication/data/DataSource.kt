package com.example.templateapplication.data

import androidx.compose.ui.graphics.Color
import com.example.templateapplication.R
import com.example.templateapplication.model.extraMateriaal.ExtraMateriaalItem
import com.example.templateapplication.model.formules.Formule
import com.example.templateapplication.ui.theme.MainColor
import com.example.templateapplication.ui.theme.MainDarkColor
import com.example.templateapplication.ui.theme.MainLightColor
import com.example.templateapplication.ui.theme.MainLighterColor
import com.example.templateapplication.ui.theme.MainLightestColor

//TODO interface van maken
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

    fun loadExtraItems(): List<ExtraMateriaalItem>{
        return listOf<ExtraMateriaalItem>(
            ExtraMateriaalItem("Stoel", "stoel", 2.99, 5, R.drawable.foto7 ),
            ExtraMateriaalItem("Tafel", "tafel", 2.99, 5, R.drawable.foto7 ),
            ExtraMateriaalItem("Stoel2", "stoel", 2.80, 1, R.drawable.foto6 ),
            ExtraMateriaalItem("Bank", "stoel", 10.0, 1, R.drawable.foto6 ),
            ExtraMateriaalItem("Stoel", "stoel", 2.99, 5, R.drawable.foto7 ),
            ExtraMateriaalItem("Tafel", "tafel", 2.99, 5, R.drawable.foto7 ),
            ExtraMateriaalItem("Stoel2", "stoel", 2.80, 1, R.drawable.foto6 ),
            ExtraMateriaalItem("Bank", "stoel", 10.0, 1, R.drawable.foto6 ),
        )
    }
}