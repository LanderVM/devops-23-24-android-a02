package com.example.templateapplication

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.templateapplication.ui.layout.BlancheAppBar
import com.example.templateapplication.ui.screens.homepage.HomeScreen
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.templateapplication.model.adres.AdresViewModel
import com.example.templateapplication.model.formules.FormuleViewModel
import com.example.templateapplication.model.klant.ContactGegevensViewModel
import com.example.templateapplication.navigation.NavigationRoutes
import com.example.templateapplication.navigation.navidrawer.NavigationDrawer
import com.example.templateapplication.ui.screens.contactgegevenspage.ConatctGegevensScreen
import com.example.templateapplication.ui.screens.evenementpage.EvenementScreen
import com.example.templateapplication.ui.screens.extraspage.ExtrasScreen
import com.example.templateapplication.ui.screens.formulepage.FormulesScreen
import com.example.templateapplication.ui.screens.overpage.OverScreen
import com.example.templateapplication.ui.screens.samenvattinggegevenspage.SamenvattingGegevensScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlancheApp(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current,
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        //val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        // VIEWMODELS
        var gegevensViewModel: ContactGegevensViewModel = viewModel()
        var adresViewModel: AdresViewModel = viewModel()
        var formuleViewModel: FormuleViewModel = viewModel()

        var selectedItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }
        ModalNavigationDrawer(
            drawerContent = {
                NavigationDrawer(
                    navController = navController,
                    selectedItemIndex = selectedItemIndex,
                    drawerState = drawerState,
                    scope = scope,
                    context = context
                )
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
                        OverScreen(
                            modifier = Modifier.padding(innerPadding),
                            navigateEmailScreen = {navController.navigate(NavigationRoutes.emailInfo.name)}
                        )
                    }
                    composable(NavigationRoutes.contactGegevens.name) {
                        ConatctGegevensScreen(
                            gegevensViewModel = gegevensViewModel,
                            adresViewModel = adresViewModel,
                            navigateSamenvatting = {navController.navigate(NavigationRoutes.samenvattingGegevens.name)},
                            modifier = Modifier.padding(innerPadding),
                        )
                    }
                    composable(NavigationRoutes.formules.name) {
                        FormulesScreen(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                    composable(NavigationRoutes.evenementGegevens.name) {
                        EvenementScreen(
                            modifier = Modifier.padding(innerPadding),
                            navigateContactGegevensScreen = {navController.navigate(NavigationRoutes.contactGegevens.name)},
                            formuleViewModel = formuleViewModel,
                            )
                    }
                    composable(NavigationRoutes.samenvattingGegevens.name) {
                        SamenvattingGegevensScreen(
                            modifier = Modifier.padding(innerPadding),
                            gegevensViewModel = gegevensViewModel,
                            adresViewModel = adresViewModel,
                            formuleViewModel = formuleViewModel,
                            navigateEventGegevens = {navController.navigate(NavigationRoutes.evenementGegevens.name)},
                            navigateContactGegevens = {navController.navigate(NavigationRoutes.contactGegevens.name)}
                        )
                    }
                    composable(NavigationRoutes.extras.name) {
                        ExtrasScreen()
                    }
                }
            }
        }
    }
}

