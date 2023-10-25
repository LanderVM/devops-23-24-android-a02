package com.example.templateapplication

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import junit.framework.TestCase.assertEquals
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
            BlancheApp(navController=navController)
        }
    }

    @Test
    fun blancheNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(NavigationRoutes.home.name)
    }
    @Test
    fun blancheNavHost_assertTopAppBarDoesntExist() {
        composeTestRule.onNodeWithTag("blancheAppBar").assertDoesNotExist()
    }
    @Test
    fun blancheNavHost_clickFormulas_navigateToFormulasScreen () {
        val sni:SemanticsNodeInteraction = composeTestRule.onNodeWithText("Formules")
        val route1 = navController.currentBackStackEntry?.destination?.route
        sni.performClick()
        val route2 = navController.currentBackStackEntry?.destination?.route
        navController.assertCurrentRouteName(NavigationRoutes.formules.name)
    }
    @Test
    fun blancheNavHost_clickOver_navigateToOverScreen () {
        val sni:SemanticsNodeInteraction = composeTestRule.onNodeWithText("Over")
        sni.performClick()
        navController.assertCurrentRouteName(NavigationRoutes.over.name)
    }
    private fun navigateToOver() {
        composeTestRule.onNodeWithText("Over").performClick()
    }
    @Test
    fun blancheNavHost_clickMeerInfo_navigateToEmailScreen () {
        navigateToOver()
        composeTestRule.onNodeWithText("Meer info").performClick()
        navController.assertCurrentRouteName(NavigationRoutes.emailInfo.name)
    }


}