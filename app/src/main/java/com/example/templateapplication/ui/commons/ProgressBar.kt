package com.example.templateapplication.ui.commons

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Composable function for displaying a linear progress indicator with a text label.
 *
 * Renders a linear progress bar along with an optional text label below it. The progression
 * of the progress bar and the text can be dynamically set.
 *
 * @param text Optional text to display below the progress bar.
 * @param progression Float value representing the progress, between 0.0 (no progress) and 1.0 (full progress).
 */
@Composable
fun ProgressBar(
    text: String = "",
    progression: Float = 0.0f
) {
    Spacer(modifier = Modifier.height(35.dp))
    LinearProgressIndicator(
        progress = { progression },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp),
        color = Color(0XFFC8A86E),
        trackColor = Color(0XFFefe5d4),
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = text,
        color = Color(0XFFC8A86E),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    )
    Spacer(modifier = Modifier.height(20.dp))
}

@Preview
@Composable
fun ProgressBarPreview(
) {
    ProgressBar(
        text = "Test",
        progression = 0.5f,
    )
}