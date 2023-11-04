package com.example.templateapplication.ui.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Titel(
    modifier: Modifier = Modifier,
    text: String = "Titel",
) {
    Spacer(modifier = Modifier.height(30.dp))
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .height(2.dp)
                .weight(1f)
                .background(Color.Gray),
        ) {}
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            overflow = TextOverflow.Ellipsis,  // Maakt text op meerdere lijnen
            modifier = Modifier.width(120.dp), // Maakt text op meerdere lijnen
        )
        Spacer(modifier = Modifier.width(20.dp))
        Box(
            modifier = Modifier
                .height(2.dp)
                .weight(1f)
                .background(Color.Gray),
        ) {}

    }
    Spacer(modifier = Modifier.height(20.dp))
}

@Preview
@Composable
fun TitelPreview(
    modifier: Modifier = Modifier,
) {
    Titel(text = "Test titel")
}
