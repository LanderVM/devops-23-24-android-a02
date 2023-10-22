package com.example.templateapplication

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.example.templateapplication.ui.screens.homepage.HomeScreen
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun homeScreen_verifyContent () {

        composeTestRule.setContent {
            HomeScreen(onAboutNavigation = { }, onFormulesNavigation = { })
        }

        composeTestRule.onNodeWithText("Over").assertExists()
        composeTestRule.onNodeWithText("Formules").assertExists()
        composeTestRule.onNodeWithTag("homeScreenTop").assertExists()
    }
}