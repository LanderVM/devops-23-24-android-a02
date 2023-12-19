package com.example.templateapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.templateapplication.ui.theme.BlancheTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BlancheTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    BlancheApp()
                }
            }
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    BlancheTheme(darkTheme = false) {
        BlancheApp()
    }
}
@Preview
@Composable
fun AppPreviewDarkMode() {
    BlancheTheme(darkTheme = true) {
        BlancheApp()
    }
}
