package com.example.templateapplication.navigation

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.templateapplication.BlancheApp
import com.example.templateapplication.R
import com.example.templateapplication.utility.assertCurrentRouteName
import com.example.templateapplication.utility.onNodeWithStringTestTag
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class BlancheAppNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupBlancheAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            BlancheApp(navController = navController, windowSize = WindowWidthSizeClass.Compact)
        }
    }

    @Test
    fun blancheNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(NavigationRoutes.Home.name)
    }

    @Test
    fun blancheNavHost_homepage_hasHamburgerMenuInsteadOfAppBar() {
        composeTestRule.onNodeWithStringTestTag(R.string.nav_appbar).assertDoesNotExist()
        composeTestRule.onNodeWithStringTestTag(R.string.nav_hamburgernav).assertExists()
    }

    @Test
    fun blancheNavHost_clickFormula_navigateToEventDetailsBasicScreen() {
        composeTestRule.onNodeWithText(
            text = composeTestRule.activity.getString(R.string.formula_basic_title),
            ignoreCase = true
        )
            .performClick()
        navController.assertCurrentRouteName(NavigationRoutes.EventDetails.name)
    }

    @Test
    fun blancheNavHost_clickFAB_navigateToPriceEstimationScreen() {
        composeTestRule.onNodeWithStringTestTag(id = R.string.priceEstimation_FAB)
            .performClick()
        navController.assertCurrentRouteName(NavigationRoutes.GuidePrice.name)
    }

    @Test
    fun blancheNavHost_clickFormula_navigateToEventDetailsAdvancedScreen() {
        composeTestRule.onNodeWithText(
            text = composeTestRule.activity.getString(R.string.formula_advanced_title),
            ignoreCase = true
        )
            .performScrollTo()
            .performClick()
        navController.assertCurrentRouteName(NavigationRoutes.EventDetails.name)
    }

    @Test
    fun blancheNavHost_goToEventDetails_canNavigateBack() {
        composeTestRule.onNodeWithText(
            text = composeTestRule.activity.getString(R.string.formula_basic_title),
            ignoreCase = true
        )
            .performClick()
        navController.assertCurrentRouteName(NavigationRoutes.EventDetails.name)
        //return to home
        composeTestRule.onNodeWithStringTestTag(R.string.backButton)
            .performClick()
        navController.assertCurrentRouteName(NavigationRoutes.Home.name)
    }


}