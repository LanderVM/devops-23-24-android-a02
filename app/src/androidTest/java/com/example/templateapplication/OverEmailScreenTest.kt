package com.example.templateapplication

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.example.templateapplication.ui.screens.homepage.HomeScreen
import com.example.templateapplication.ui.screens.overpage.EmailForInformationScreen
import org.junit.Rule
import org.junit.Test

class OverEmailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun homeScreen_verifyContent () {

        composeTestRule.setContent {
            EmailForInformationScreen()
        }

        composeTestRule.onNodeWithText("Gelieve hieronder uw email in te vullen en op de knop \"verstuur\" te drukken om meer informatie betreffende de foodtruck te ontvangen.")
            .assertExists()

        composeTestRule.onNodeWithText("email").assertExists()

        composeTestRule.onNodeWithText("Verstuur").assertExists().assertIsNotEnabled()
    }
}