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

@Composable
fun ProgressieBar(
    modifier: Modifier = Modifier,
    text: String = "",
    progression: Float = 0.0f
) {
    Spacer(modifier = Modifier.height(35.dp))
    LinearProgressIndicator(
        progress = { progression },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp),
        color = Color(android.graphics.Color.parseColor("#C8A86E")),
        trackColor = Color(android.graphics.Color.parseColor("#efe5d4")),
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = text,
        color = Color(android.graphics.Color.parseColor("#C8A86E")),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    )
    Spacer(modifier = Modifier.height(20.dp))
}

@Preview
@Composable
fun ProgressieBarPreview(
    modifier: Modifier = Modifier,
) {
    ProgressieBar(
        text = "Test",
        progression = 0.5f,
    )
}