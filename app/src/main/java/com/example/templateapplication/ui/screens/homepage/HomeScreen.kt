package com.example.templateapplication.ui.screens.homepage

import com.example.templateapplication.ui.screens.homepage.components.FormuleCard
import com.example.templateapplication.ui.screens.homepage.components.HomeScreenTop
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.templateapplication.R

@Composable
fun HomeScreen(
    openDrawer: () -> Unit = {},
    onAboutNavigation: () -> Unit,
    onFormulesNavigation: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val image = painterResource(id = R.drawable.sfeer_foto_1)

    LazyColumn(
    ) {
        item {
            HomeScreenTop(openDrawer = openDrawer)
        }
        item {
            FormuleCard("Over", image, onButtonClicked = onAboutNavigation )
        }
        item {
            FormuleCard("Formules", image, onButtonClicked = onFormulesNavigation)
        }
    }
}