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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.templateapplication.model.adres.AdresViewModel
import com.example.templateapplication.model.extraMateriaal.ExtraItemViewModel
import com.example.templateapplication.model.formules.FormulaViewModel
import com.example.templateapplication.model.guidePriceEstimation.PriceEstimationViewModel
import com.example.templateapplication.model.home.HomeViewModel
import com.example.templateapplication.model.klant.ContactGegevensViewModel
import com.example.templateapplication.navigation.NavigationRoutes
import com.example.templateapplication.navigation.navidrawer.NavigationDrawer
import com.example.templateapplication.ui.layout.BlancheAppBar
import com.example.templateapplication.ui.screens.QuotationRequestViewModel
import com.example.templateapplication.ui.screens.contactgegevenspage.ConatctGegevensScreen
import com.example.templateapplication.ui.screens.quotationRequest.EventDetailsScreen
import com.example.templateapplication.ui.screens.extraspage.ExtrasScreen
import com.example.templateapplication.ui.screens.formulepage.FormulesScreen
import com.example.templateapplication.ui.screens.guideprice.GuidePriceScreen
import com.example.templateapplication.ui.screens.homepage.HomeScreen
import com.example.templateapplication.ui.screens.overpage.OverScreen
import com.example.templateapplication.ui.screens.samenvattinggegevenspage.SamenvattingGegevensScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlancheApp(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        // val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        // VIEWMODELS
        var gegevensViewModel: ContactGegevensViewModel = viewModel()
        var homeViewModel: HomeViewModel = viewModel()
        var adresViewModel: AdresViewModel = viewModel()
        var formulaViewModel: FormulaViewModel = viewModel()
        var extraItemViewModel: ExtraItemViewModel = viewModel(factory = ExtraItemViewModel.Factory)
        var quotationRequestViewModel: QuotationRequestViewModel = viewModel(factory = QuotationRequestViewModel.Factory)
        var priceEstimationViewModel: PriceEstimationViewModel =
            viewModel(factory = PriceEstimationViewModel.Factory)

        var selectedItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }
        ModalNavigationDrawer(
            gesturesEnabled = true, // Swipe voor navigatiebar
            drawerContent = {
                NavigationDrawer(
                    navController = navController,
                    selectedItemIndex = selectedItemIndex,
                    drawerState = drawerState,
                    scope = scope,
                    context = context
                )
            },
            drawerState = drawerState
        ) {
            Scaffold(
                topBar = {
                    if (backStackEntry?.destination?.route != NavigationRoutes.Home.name) {
                        BlancheAppBar(
                            currentScreen = NavigationRoutes.valueOf(
                                backStackEntry?.destination?.route ?: NavigationRoutes.Home.name
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
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = NavigationRoutes.Home.name,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(NavigationRoutes.Home.name) {
                        HomeScreen(
                            openDrawer = {
                                scope.launch {
                                    drawerState.open()
                                }
                            },
                            modifier = Modifier.padding(innerPadding),
                            onExtraNavigation = { navController.navigate(NavigationRoutes.ExtrasOverview.name) },
                            onBasicNavigation = {
                                quotationRequestViewModel.selectFormula(1)
                                navController.navigate(NavigationRoutes.EventDetails.name)
                            },
                            onAllInNavigation = {
                                quotationRequestViewModel.selectFormula(2)
                                navController.navigate(NavigationRoutes.EventDetails.name)
                            },
                            onGevorderedNavigation = {
                                quotationRequestViewModel.selectFormula(3)
                                navController.navigate(NavigationRoutes.EventDetails.name)
                            },
                            onGuidePriceNavigation = {
                                navController.navigate(NavigationRoutes.GuidePrice.name)
                            },
                            homeViewModel = homeViewModel
                        )
                    }
                    composable(NavigationRoutes.About.name) {
                        OverScreen(
                            modifier = Modifier.padding(innerPadding),
                            navigateEmailScreen = { navController.navigate(NavigationRoutes.AboutEmail.name) },
                        )
                    }
                    composable(NavigationRoutes.ContactDetails.name) {
                        ConatctGegevensScreen(
                            gegevensViewModel = gegevensViewModel,
                            adresViewModel = adresViewModel,
                            navigateExtras = { navController.navigate(NavigationRoutes.ExtraItems.name) },
                            modifier = Modifier.padding(innerPadding),
                        )
                    }
                    composable(NavigationRoutes.Formulas.name) {
                        FormulesScreen(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                    composable(NavigationRoutes.EventDetails.name) {
                        EventDetailsScreen(
                            modifier = Modifier.padding(innerPadding),
                            navigateContactGegevensScreen = {
                                navController.navigate(
                                    NavigationRoutes.ContactDetails.name
                                )
                            },
                            quotationRequestViewModel = quotationRequestViewModel,
                        )
                    }
                    composable(NavigationRoutes.SummaryData.name) {
                        SamenvattingGegevensScreen(
                            modifier = Modifier.padding(innerPadding),
                            gegevensViewModel = gegevensViewModel,
                            adresViewModel = adresViewModel,
                            formulaViewModel = formulaViewModel,
                            extraItemViewModel = extraItemViewModel,
                            eventAddressViewModel = quotationRequestViewModel,
                            navigateEventGegevens = { navController.navigate(NavigationRoutes.EventDetails.name) },
                            navigateContactGegevens = { navController.navigate(NavigationRoutes.ContactDetails.name) },
                            navigateExtras = { navController.navigate(NavigationRoutes.ExtraItems.name) },
                        )
                    }
                    composable(NavigationRoutes.ExtraItems.name) {
                        ExtrasScreen(
                            modifier = Modifier.padding(innerPadding),
                            extraItemViewModel = extraItemViewModel,
                            navigateSamenvatting = { navController.navigate(NavigationRoutes.SummaryData.name) },
                            isOverview = false
                        )
                    }
                    composable(NavigationRoutes.ExtrasOverview.name) {
                        ExtrasScreen(
                            modifier = Modifier.padding(innerPadding),
                            extraItemViewModel = extraItemViewModel,
                            navigateSamenvatting = { navController.navigate(NavigationRoutes.SummaryData.name) },
                            isOverview = true
                        )
                    }
                    composable(NavigationRoutes.GuidePrice.name) {
                        GuidePriceScreen(
                            modifier = Modifier.padding(innerPadding),
                            formulaViewModel = formulaViewModel,
                            quotationRequestViewModel = quotationRequestViewModel,
                            priceEstimationViewModel = priceEstimationViewModel
                        )
                    }
                }
            }
        }
    }
}
