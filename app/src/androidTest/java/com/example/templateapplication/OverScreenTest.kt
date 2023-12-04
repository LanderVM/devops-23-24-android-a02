package com.example.templateapplication

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.example.templateapplication.ui.screens.aboutPage.AboutScreen
import org.junit.Rule
import org.junit.Test

class OverScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun overScreen_verifyContent () {

        composeTestRule.setContent {
            AboutScreen( navigateEmailScreen = {})
        }

        composeTestRule.onNodeWithText("Over de foodtruck").assertExists()
        composeTestRule.onNodeWithText("Minimaal te voorziene ruimte:").assertExists()
        composeTestRule.onNodeWithText("5m x 2m x 2m").assertExists()
        composeTestRule.onNodeWithText("Benodigtheden:").assertExists()
        composeTestRule.onNodeWithText("Iemand ter plaatse").assertExists()
        composeTestRule.onNodeWithText("Stopcontact").assertExists()
        composeTestRule.onNodeWithText("Doorgang:").assertExists()
        composeTestRule.onNodeWithText("Smalste punt op de route naar").assertExists()
        composeTestRule.onNodeWithText("plaats van opstelling dient minimaal").assertExists()
        composeTestRule.onNodeWithText("3m te zijn!").assertExists()

        composeTestRule.onNodeWithContentDescription("foto van de foodtruck").assertExists()
        composeTestRule.onNodeWithContentDescription("foto van mensen die glazen inschenken").assertExists()
        composeTestRule.onNodeWithContentDescription("foto van ingeschonken glas").assertExists()
        composeTestRule.onNodeWithContentDescription("foto van een tap").assertExists()
    }
}