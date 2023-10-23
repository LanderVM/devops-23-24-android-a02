package com.example.templateapplication

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.templateapplication.model.navidrawer.NavigationItem
import com.example.templateapplication.ui.layout.BlancheAppBar
import com.example.templateapplication.ui.screens.gegevenspage.GegevensScreen
import com.example.templateapplication.ui.screens.homepage.HomeScreen
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.templateapplication.ui.screens.overpage.AboutScreen
import com.example.templateapplication.ui.screens.overpage.EmailForInformationScreen
import com.example.templateapplication.ui.screens.formulepage.FormulesScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlancheApp(
    navController: NavHostController = rememberNavController()
) {
    val navItems = listOf(
        NavigationItem(
            title = NavigationRoutes.home.name,
        ),
        NavigationItem(
            title = NavigationRoutes.over.name,
        ),
        NavigationItem(
            title = NavigationRoutes.gegevens.name,
        ),
        NavigationItem(
            title = NavigationRoutes.formules.name,
        ),
    )
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        //val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet(
                    drawerContentColor = Color.White,
                    modifier = Modifier
                        .alpha(0.9f)
                ) {
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    navItems.forEachIndexed { index, item ->
                        Divider(
                            color = Color.DarkGray,
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .padding(10.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        val isSelected = index == selectedItemIndex

                        NavigationDrawerItem(
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = Color.Transparent
                            ),
                            label = {
                                Text(
                                    text = item.title.uppercase(),
                                    fontSize = 40.sp,
                                )
                            },
                            selected = index == selectedItemIndex,
                            onClick = {
                                navController.navigate(item.title)
                                selectedItemIndex = index
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            modifier = Modifier
                                .padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            },
            drawerState = drawerState,
        ) {
            Scaffold(
                topBar = {
                    if (backStackEntry?.destination?.route != NavigationRoutes.home.name) {
                        BlancheAppBar(
                            currentScreen = NavigationRoutes.valueOf(
                                backStackEntry?.destination?.route ?: NavigationRoutes.home.name
                            ),
                            canNavigateBack = navController.previousBackStackEntry != null,
                            navigateUp = { navController.navigateUp() },
                            openDrawer = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        )
                    }
                },
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = NavigationRoutes.home.name,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(NavigationRoutes.home.name) {
                        HomeScreen(
                            openDrawer = {
                                scope.launch {
                                    drawerState.open()
                                }
                            },
                            modifier = Modifier.padding(innerPadding),
                            onAboutNavigation = {navController.navigate(NavigationRoutes.over.name)},
                            onFormulesNavigation = {navController.navigate(NavigationRoutes.formules.name)},
                            )
                    }
                    composable(NavigationRoutes.over.name) {
                        AboutScreen(
                            modifier = Modifier.padding(innerPadding),
                            navigateEmailScreen = {navController.navigate(NavigationRoutes.emailInfo.name)}
                        )
                    }
                    composable(NavigationRoutes.gegevens.name) {
                        GegevensScreen(
                            modifier = Modifier.padding(innerPadding),
                        )
                    }
                    composable(NavigationRoutes.formules.name) {
                        FormulesScreen(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                    composable(NavigationRoutes.emailInfo.name) {
                        EmailForInformationScreen(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

