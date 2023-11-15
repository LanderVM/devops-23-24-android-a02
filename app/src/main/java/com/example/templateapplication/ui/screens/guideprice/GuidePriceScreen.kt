package com.example.templateapplication.ui.screens.guideprice

import androidx.compose.runtime.Composable
import com.example.templateapplication.data.Datasource

@Composable
fun GuidePriceScreen() {
    val formulasList = Datasource().loadFormules()
}
