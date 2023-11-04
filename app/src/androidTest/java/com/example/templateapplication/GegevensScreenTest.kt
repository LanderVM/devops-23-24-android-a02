package com.example.templateapplication

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.testing.TestNavHostController
import com.example.templateapplication.navigation.NavigationRoutes
import com.example.templateapplication.ui.screens.contactgegevenspage.ConatctGegevensScreen
import org.junit.Rule
import org.junit.Test

class GegevensScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Test
    fun homeScreen_verifyContent () {

        composeTestRule.setContent {
            ConatctGegevensScreen(navigateSamenvatting = {})
        }

        composeTestRule.onNodeWithText("Personalia").assertExists()
        composeTestRule.onNodeWithText("Contact gegevens").assertExists()
        composeTestRule.onNodeWithText("Naam").assertExists()
        composeTestRule.onNodeWithText("Voornaam").assertExists()
        composeTestRule.onNodeWithText("Type Evenement").assertExists()
        composeTestRule.onNodeWithText("Email").assertExists()
        composeTestRule.onNodeWithText("Adres gegevens").assertExists()
        composeTestRule.onNodeWithText("Straat").assertExists()
        composeTestRule.onNodeWithText("Huisnummer").assertExists()
        composeTestRule.onNodeWithText("Gemeente").assertExists()
        composeTestRule.onNodeWithText("Postcode").assertExists()

        composeTestRule.onNodeWithText("Volgende").assertExists().assertIsNotEnabled()
    }
}