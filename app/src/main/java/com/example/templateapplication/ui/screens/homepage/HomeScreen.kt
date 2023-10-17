package com.example.templateapplication.ui.screens.homepage

import com.example.templateapplication.ui.screens.homepage.components.FormuleCard
import com.example.templateapplication.ui.screens.homepage.components.HomeScreenTop
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.templateapplication.NavigationRoutes
import com.example.templateapplication.R

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.sfeer_foto_1)

    LazyColumn(
    ) {
        item {
            HomeScreenTop()
        }
        item {
            FormuleCard("Over pagina", image, { navController.navigate(NavigationRoutes.over.name) })
        }
        item {
            FormuleCard("Gegevens pagina", image, { navController.navigate(NavigationRoutes.gegevens.name) })
        }
    }
}