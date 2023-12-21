package com.example.templateapplication.navigation

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.templateapplication.BlancheApp
import com.example.templateapplication.utility.assertCurrentRouteName
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.templateapplication.R
import com.example.templateapplication.utility.onNodeWithStringTestTag


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
            BlancheApp(navController=navController, windowSize = WindowWidthSizeClass.Compact)
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
    fun blancheNavHost_clickExtraMaterial_navigateToExtraMaterialsScreen () {
        composeTestRule.onNodeWithStringTestTag(R.string.nav_formulaCard).performClick()
        navController.assertCurrentRouteName(NavigationRoutes.ExtrasOverview.name)
    }
}