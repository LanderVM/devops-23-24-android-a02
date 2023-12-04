package com.example.templateapplication.ui.screens.homepage

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
    onBasicNavigation: () -> Unit,
    onAllInNavigation: () -> Unit,
    onGevorderedNavigation: () -> Unit,
    onGuidePriceNavigation: () -> Unit,
    homeViewModel: HomeViewModel
) {
    val image1 = painterResource(id = R.drawable.foto4)
    val image2 = painterResource(id = R.drawable.foto5)
    val image3 = painterResource(id = R.drawable.foto8)
    val image4 = painterResource(id = R.drawable.foto7)

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
            item { FormuleCard(title = stringResource(id = R.string.formula_basic_title), image = image1, onButtonClicked = onBasicNavigation) }
            item { FormuleCard(title = stringResource(id = R.string.formula_allIn_title), image = image2, onButtonClicked = onAllInNavigation) }
            item { FormuleCard(title = stringResource(id = R.string.formula_advanced_title), image = image3, onButtonClicked = onGevorderedNavigation) }
            item { FormuleCard(title = stringResource(id = R.string.formula_extraMaterials_title), image = image4, onButtonClicked = onExtraNavigation) }
        }
    }
}

