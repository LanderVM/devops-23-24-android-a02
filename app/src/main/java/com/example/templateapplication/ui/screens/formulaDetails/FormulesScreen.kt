package com.example.templateapplication.ui.screens.formulaDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.templateapplication.ui.screens.formulaDetails.components.FormulaDetailsCard
import com.example.templateapplication.ui.theme.MainColor

@Composable
fun FormulesScreen(
    modifier: Modifier = Modifier,
    formulasViewModel: FormulasViewModel,
) {
    val formulaList by formulasViewModel.formulaList.collectAsState()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(formulaList.formulaListState) { index, formula ->
            FormulaDetailsCard(formula = formula, backgroundColor = if (index % 2 == 0) Color.White else MainColor)
        }
    }
}