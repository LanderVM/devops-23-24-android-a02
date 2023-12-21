package com.example.templateapplication.ui.screens.homepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.templateapplication.R
import com.example.templateapplication.model.common.quotation.Formula
import com.example.templateapplication.model.home.HomeViewModel
import com.example.templateapplication.ui.screens.homepage.components.FormulaCard
import com.example.templateapplication.ui.screens.homepage.components.HomeScreenTop
import com.example.templateapplication.ui.utils.ReplyNavigationType

@Composable
fun HomeScreen(
    navigationType: ReplyNavigationType,
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit = {},
    onExtraNavigation: () -> Unit,
    onQuotationRequestNavigation: (Formula) -> Unit,
    onGuidePriceNavigation: () -> Unit,
    homeViewModel: HomeViewModel
) {
    val formulaList by homeViewModel.formulaList.collectAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    homeViewModel.updateOpenDialog(false)
                    onGuidePriceNavigation()
                },
                content = {
                    Icon(Icons.Filled.Info, contentDescription = "Localized description")
                },
                modifier = Modifier.padding(all = 16.dp)
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(250.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.padding(paddingValues)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                HomeScreenTop(navigationType = navigationType ,openDrawer = openDrawer)
            }
            items(formulaList.formulaListState) { formula ->
                FormulaCard(
                    title = formula.title,
                    image = formula.imageUrl,
                    onButtonClicked = { onQuotationRequestNavigation(formula) }
                )
            }
            item {
                FormulaCard(
                    modifier = Modifier.testTag(stringResource(R.string.nav_formulaCard)),
                    title = stringResource(id = R.string.formula_extraMaterials_title),
                    onButtonClicked = onExtraNavigation
                )
            }
        }
    }
}
