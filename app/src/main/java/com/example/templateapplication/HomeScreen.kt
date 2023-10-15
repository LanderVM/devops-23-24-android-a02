package com.example.templateapplication

import FormuleCard
import HomeScreenTop
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.sfeer_foto_1)

    LazyColumn(
        /*modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()*/
    ) {
        item {
            HomeScreenTop()
        }
        // Mock data
        items(3) { index ->
            FormuleCard("Categorië item", image)
        }
    }
}