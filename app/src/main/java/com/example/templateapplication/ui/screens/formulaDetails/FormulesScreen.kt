package com.example.templateapplication.ui.screens.formulaDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.templateapplication.data.Datasource
import com.example.templateapplication.ui.screens.formulaDetails.components.FormulaDetailsCard

import com.example.templateapplication.ui.theme.MainColor
import com.example.templateapplication.ui.utils.ReplyNavigationType

@Composable
fun FormulesScreen(
    navigationType: ReplyNavigationType,
    modifier: Modifier = Modifier
) {
    val formuleList = Datasource().loadFormules()

    var columnAmount : Int
    var padding : Dp
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
        itemsIndexed(formuleList) { index, formuleData ->
            FormulaDetailsCard(navigationType= navigationType ,formula = formuleData, backgroundColor = if (index % 2 == 0) Color.White else MainColor)
        }
    }
}