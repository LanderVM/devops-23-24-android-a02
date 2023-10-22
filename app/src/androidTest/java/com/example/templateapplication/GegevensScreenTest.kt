package com.example.templateapplication

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.example.templateapplication.ui.screens.gegevenspage.GegevensScreen
import com.example.templateapplication.ui.screens.homepage.HomeScreen
import org.junit.Rule
import org.junit.Test

class GegevensScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun homeScreen_verifyContent () {

        composeTestRule.setContent {
            GegevensScreen()
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