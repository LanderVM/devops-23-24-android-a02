package com.example.templateapplication.ui.screens.overpage

import android.graphics.Paint.Align
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.example.templateapplication.ui.screens.overpage.components.InfoParts
import com.example.templateapplication.ui.theme.ImperialScript

@Composable
fun OverScreen(navController: NavController, modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.sfeer_foto_1)

    LazyColumn(
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Text(
                    text = "Over de foodtruck",
                    fontFamily = ImperialScript,
                    fontSize = 50.sp,
                    color = Color.DarkGray,
                )
                Spacer(modifier = Modifier.height(20.dp))
                InfoParts()
            }
        }

    }
}