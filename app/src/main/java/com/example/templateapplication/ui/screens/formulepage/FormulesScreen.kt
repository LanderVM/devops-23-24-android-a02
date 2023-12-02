package com.example.templateapplication.ui.screens.formulepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.templateapplication.data.Datasource
import com.example.templateapplication.ui.screens.formulepage.components.DetailsFormuleCard
import com.example.templateapplication.ui.theme.MainColor

@Composable
fun FormulesScreen(
    modifier: Modifier = Modifier
) {
    val formuleList = Datasource().loadFormules()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(formuleList) { index, formuleData ->
            DetailsFormuleCard(formule = formuleData, backgroundColor = if (index % 2 == 0) Color.White else MainColor)
        }
    }
}