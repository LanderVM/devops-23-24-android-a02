package com.example.templateapplication.ui.screens.formulepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.templateapplication.data.formules.FormuleItem
import com.example.templateapplication.ui.screens.formulepage.components.DetailsFormuleCard
import com.example.templateapplication.ui.theme.MainColor

@Composable
fun FormulesScreen(
    modifier: Modifier = Modifier
) {
    // Mock data
    val basis = listOf("Foodtruck", "Tap")
    val formuleBasis = FormuleItem("Basis", basis)

    val allIn = listOf("Foodtruck", "Tap", "BBQ", "Decoratie")
    val formuleAllIn = FormuleItem("All-In", allIn)

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        DetailsFormuleCard(formule = formuleBasis, backgroundColor = Color.White, textColor = Color.DarkGray)
        DetailsFormuleCard(formule = formuleAllIn, backgroundColor = MainColor, textColor = Color.Black)
    }

}