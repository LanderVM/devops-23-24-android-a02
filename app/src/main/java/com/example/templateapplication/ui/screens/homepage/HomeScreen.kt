package com.example.templateapplication.ui.screens.homepage

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.templateapplication.R
import com.example.templateapplication.model.home.HomeViewModel
import com.example.templateapplication.ui.commons.AlertPopUp
import com.example.templateapplication.ui.screens.homepage.components.FormuleCard
import com.example.templateapplication.ui.screens.homepage.components.HomeScreenTop

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit = {},
    onExtraNavigation: () -> Unit,
    onBasicNavigation: () -> Unit,
    onAllInNavigation: () -> Unit,
    onGevorderedNavigation: () -> Unit,
    onGuidePriceNavigation: () -> Unit,
    homeViewModel: HomeViewModel
) {
    val homeUiState by homeViewModel.homeUiState.collectAsState()

    when {
        homeUiState.openDialog -> {
            AlertPopUp(
                onDismissRequest = { homeViewModel.updateOpenDialog(false)  },
                onConfirmation = {
                    homeViewModel.updateOpenDialog(false)
                    onGuidePriceNavigation()
                },
                dialogTitle = stringResource(R.string.alertPopUp_title),
                dialogText = stringResource(R.string.alertPopUp_text),
                confirmText = stringResource(R.string.alertPopUp_confirm),
                dismissText = stringResource(R.string.alertPopUp_dismiss)
            )
        }
    }

    val image1 = painterResource(id = R.drawable.foto4)
    val image2 = painterResource(id = R.drawable.foto5)
    val image3 = painterResource(id = R.drawable.foto8)
    val image4 = painterResource(id = R.drawable.foto7)

    LazyColumn {
        item {
            HomeScreenTop(openDrawer = openDrawer)
        }
        item {
            FormuleCard(stringResource(id = R.string.formula_basic_title), image1, onButtonClicked = onBasicNavigation)
        }
        item {
            FormuleCard(stringResource(id = R.string.formula_allIn_title), image2, onButtonClicked = onAllInNavigation)
        }
        item {
            FormuleCard(stringResource(id = R.string.formula_advanced_title), image3, onButtonClicked = onGevorderedNavigation)
        }
        item {
            FormuleCard(stringResource(id = R.string.formula_extraMaterials_title), image4, onButtonClicked = onExtraNavigation)
        }
    }
}
