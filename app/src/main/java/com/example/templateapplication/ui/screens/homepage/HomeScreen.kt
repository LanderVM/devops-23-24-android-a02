package com.example.templateapplication.ui.screens.homepage

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.templateapplication.R
import com.example.templateapplication.model.home.HomeViewModel
import com.example.templateapplication.ui.screens.homepage.components.FormuleCard
import com.example.templateapplication.ui.screens.homepage.components.HomeScreenTop

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit = {},
    onExtraNavigation: () -> Unit,
    onQuotationRequestNavigation: (Int) -> Unit,
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
        LazyColumn(modifier.padding(paddingValues)) {
            item { HomeScreenTop(openDrawer = openDrawer) }
            items(formulaList.formulaListState) { formula ->
                FormuleCard(
                    title = formula.title,
                    image = formula.imageUrl,
                    onButtonClicked = { onQuotationRequestNavigation(formula.id) }
                )
            }
            item {
                FormuleCard(
                    title = stringResource(id = R.string.formula_extraMaterials_title),
                    onButtonClicked = onExtraNavigation
                )
            }
        }
    }
}

