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
    onExtraNavigation: () -> Unit,
    onBasicNavigation: () -> Unit,
    onAllInNavigation: () -> Unit,
    onGevorderedNavigation: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val image1 = painterResource(id = R.drawable.foto4)
    val image2 = painterResource(id = R.drawable.foto5)
    val image3 = painterResource(id = R.drawable.foto8)
    val image4 = painterResource(id = R.drawable.foto7)


    LazyColumn(
    ) {
        item {
            HomeScreenTop(openDrawer = openDrawer)
        }
        item {
            FormuleCard("Basic", image1, onButtonClicked = onBasicNavigation)
        }
        item {
            FormuleCard("All-In", image2, onButtonClicked = onAllInNavigation)
        }
        item {
            FormuleCard("Gevorderd", image3, onButtonClicked = onGevorderedNavigation)
        }
        item {
            FormuleCard("Extra Materiaal", image4, onButtonClicked = onExtraNavigation )
        }

    }
}