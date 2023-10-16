package com.example.templateapplication.ui.maincomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.templateapplication.NavigationRoutes
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.templateapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlancheAppBar(
    modifier: Modifier = Modifier,

    currentScreen: NavigationRoutes,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    openDrawer: () -> Unit = {},
) {
    val image = painterResource(id = R.drawable.food_truck_logo)

    Surface(shadowElevation = 10.dp) {

        TopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = Color.White,
            ),
            title = {
                if (currentScreen != NavigationRoutes.home) {
                    Text(stringResource(id = currentScreen.title))
                }
            },
            actions = {
                Row(
                    modifier = Modifier
                        .padding(end = 16.dp)
                ) {
                    if (canNavigateBack) {
                        IconButton(
                            onClick = navigateUp,
                        ) {
                            Image(
                                painter = image,
                                contentDescription = "navigate back",
                            )
                        }
                    }

                    IconButton(onClick = openDrawer) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                }
            }
        )
    }

}







