package com.example.templateapplication.ui.screens.formulaDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.templateapplication.ui.screens.formulaDetails.components.FormulaDetailsCard

import com.example.templateapplication.ui.theme.MainColor
import com.example.templateapplication.ui.utils.ReplyNavigationType

@Composable
fun FormulasScreen(
    formulasViewModel: FormulasViewModel,
    navigationType: ReplyNavigationType,
) {
    val formulaList by formulasViewModel.formulaList.collectAsState()

    val columnAmount: Int
    val padding: Dp
    when (navigationType) {
        ReplyNavigationType.NAVIGATION_RAIL -> {
            columnAmount = 1
            padding = 100.dp
        }

        ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            columnAmount = 3
            padding = 0.dp
        }

        else -> {
            columnAmount = 1
            padding = 0.dp
        }
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(columnAmount),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = padding)
    ) {
        itemsIndexed(formulaList.formulaListState) { index, formula ->
            FormulaDetailsCard(
                navigationType = navigationType,
                formula = formula,
                backgroundColor = if (index % 2 == 0) Color.White else MainColor
            )
        }
    }
}