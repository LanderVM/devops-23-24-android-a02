package com.example.templateapplication.ui.screens.guideprice

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.data.Datasource

@Composable
fun GuidePriceScreen(
    modifier: Modifier = Modifier,
    guidePriceViewModel: ViewModel = viewModel()
) {
    val formulasList = Datasource().loadFormules()
}
