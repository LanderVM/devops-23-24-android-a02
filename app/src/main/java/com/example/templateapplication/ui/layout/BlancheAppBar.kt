package com.example.templateapplication.ui.layout

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.templateapplication.navigation.NavigationRoutes
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.testTag
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
    if (currentScreen != NavigationRoutes.Home) {
        Surface(shadowElevation = 10.dp) {
            TopAppBar(
                title = {
                    Text(stringResource(id = currentScreen.title))
                },
                actions = {
                    Row(
                        modifier = Modifier
                            .padding(end = 16.dp, start = 16.dp)
                    ) {
                        if (canNavigateBack) {
                            IconButton(
                                modifier=Modifier.testTag(stringResource(id = R.string.backButton)),
                                onClick = navigateUp,
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Localized description"
                                )
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

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
}






