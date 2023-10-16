package com.example.templateapplication.ui.screens.overpage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import com.example.templateapplication.ui.screens.homepage.components.FormuleCard
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.templateapplication.NavigationRoutes
import com.example.templateapplication.R
import com.example.templateapplication.ui.theme.ImperialScript

@Composable
fun OverScreen(navController: NavController, modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.sfeer_foto_1)

    LazyColumn(
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Over de foodtruck",
                    fontFamily = ImperialScript,
                    fontSize = 100.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(top = 50.dp)
                )
            }
        }
        items(3) { index ->
            FormuleCard("Categorië item", image,
                { navController.navigate(NavigationRoutes.home.name) })
        }
    }
}