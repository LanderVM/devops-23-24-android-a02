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
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.templateapplication.model.home.HomeViewModel
import com.example.templateapplication.navigation.NavigationRoutes
import com.example.templateapplication.navigation.navidrawer.NavigationDrawer
import com.example.templateapplication.ui.layout.BlancheAppBar
import com.example.templateapplication.ui.screens.aboutPage.AboutScreen
import com.example.templateapplication.ui.screens.aboutPage.AboutViewModel
import com.example.templateapplication.ui.screens.equipmentOverviewPage.EquipmentOverviewScreen
import com.example.templateapplication.ui.screens.equipmentOverviewPage.EquipmentOverviewViewModel
import com.example.templateapplication.ui.screens.formulaDetails.FormulasViewModel
import com.example.templateapplication.ui.screens.formulaDetails.FormulasScreen
import com.example.templateapplication.ui.screens.homepage.HomeScreen
import com.example.templateapplication.ui.screens.priceEstimation.GuidePriceScreen
import com.example.templateapplication.ui.screens.priceEstimation.PriceEstimationViewModel
import com.example.templateapplication.ui.screens.quotationRequest.EventDetailsScreen
import com.example.templateapplication.ui.screens.quotationRequest.ExtrasScreen
import com.example.templateapplication.ui.screens.quotationRequest.PersonalDetailsScreen
import com.example.templateapplication.ui.screens.quotationRequest.QuotationRequestViewModel
import com.example.templateapplication.ui.screens.quotationRequest.SummaryScreen
import com.example.templateapplication.ui.utils.ReplyNavigationType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlancheApp(
    windowSize: WindowWidthSizeClass,
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current,
) {

    val navigationType: ReplyNavigationType = when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            ReplyNavigationType.BOTTOM_NAVIGATION
        }

        WindowWidthSizeClass.Medium -> {
            ReplyNavigationType.NAVIGATION_RAIL
        }

        WindowWidthSizeClass.Expanded -> {
            ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
        }

        else -> {
            ReplyNavigationType.BOTTOM_NAVIGATION
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        val aboutViewModel: AboutViewModel = viewModel(factory = AboutViewModel.Factory)
        val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
        val quotationRequestViewModel: QuotationRequestViewModel =
            viewModel(factory = QuotationRequestViewModel.Factory)
        val priceEstimationViewModel: PriceEstimationViewModel =
            viewModel(factory = PriceEstimationViewModel.Factory)
        val equipmentOverviewViewModel: EquipmentOverviewViewModel =
            viewModel(factory = EquipmentOverviewViewModel.Factory)
        val formulasViewModel: FormulasViewModel = viewModel(factory = FormulasViewModel.Factory)

        val selectedItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }
        ModalNavigationDrawer(
            modifier = Modifier.testTag(stringResource(R.string.nav_hamburgernav)),
            gesturesEnabled = true,
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
                            modifier = Modifier.testTag(stringResource(R.string.nav_hamburgernav)),
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
                            navigationType = navigationType,
                            openDrawer = {
                                scope.launch {
                                    drawerState.open()
                                }
                            },
                            modifier = Modifier.padding(innerPadding),
                            onExtraNavigation = { navController.navigate(NavigationRoutes.ExtrasOverview.name) },
                            onQuotationRequestNavigation = { formula ->
                                quotationRequestViewModel.selectFormula(formula)
                                navController.navigate(NavigationRoutes.EventDetails.name)
                            },
                            onGuidePriceNavigation = {
                                navController.navigate(NavigationRoutes.GuidePrice.name)
                            },
                            homeViewModel = homeViewModel
                        )
                    }
                    composable(NavigationRoutes.About.name) {
                        AboutScreen(
                            navigationType = navigationType,
                            modifier = Modifier.padding(innerPadding),
                            aboutViewModel = aboutViewModel,
                        )
                    }
                    composable(NavigationRoutes.ContactDetails.name) {
                        PersonalDetailsScreen(
                            navigationType = navigationType,
                            navigateExtras = { navController.navigate(NavigationRoutes.ExtraItems.name) },
                            quotationRequestViewModel = quotationRequestViewModel,
                        )
                    }
                    composable(NavigationRoutes.Formulas.name) {
                        FormulasScreen(
                            navigationType = navigationType,
                            formulasViewModel = formulasViewModel,
                        )
                    }
                    composable(NavigationRoutes.EventDetails.name) {
                        EventDetailsScreen(
                            navigationType = navigationType,
                            navigateContactDetailsScreen = {
                                navController.navigate(
                                    NavigationRoutes.ContactDetails.name
                                )
                            },
                            quotationRequestViewModel = quotationRequestViewModel,
                        )
                    }
                    composable(NavigationRoutes.SummaryData.name) {
                        SummaryScreen(
                            navigationType = navigationType,
                            quotationRequestViewModel = quotationRequestViewModel,
                            navigateEventDetails = { navController.navigate(NavigationRoutes.EventDetails.name) },
                            navigateContactDetails = { navController.navigate(NavigationRoutes.ContactDetails.name) },
                            navigateExtras = { navController.navigate(NavigationRoutes.ExtraItems.name) },
                        )
                    }
                    composable(NavigationRoutes.ExtraItems.name) {
                        ExtrasScreen(
                            navigationType = navigationType,
                            quotationRequestViewModel = quotationRequestViewModel,
                            navigateSummary = { navController.navigate(NavigationRoutes.SummaryData.name) },
                            isOverview = false
                        )
                    }
                    composable(NavigationRoutes.ExtrasOverview.name) {
                        EquipmentOverviewScreen(
                            navigationType = navigationType,
                            equipmentOverviewViewModel = equipmentOverviewViewModel
                        )
                    }
                    composable(NavigationRoutes.GuidePrice.name) {
                        GuidePriceScreen(
                            navigationType = navigationType,
                            priceEstimationViewModel = priceEstimationViewModel
                        )
                    }
                }
            }
        }
    }
}
