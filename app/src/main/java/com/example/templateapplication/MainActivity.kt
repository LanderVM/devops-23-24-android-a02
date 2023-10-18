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
import com.example.templateapplication.ui.screens.aboutpage.AboutScreen
import com.example.templateapplication.ui.theme.TemplateApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /*TemplateApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BlancheApp()
                }
            }*/
            AboutScreen()


        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TemplateApplicationTheme {
        BlancheApp()
    }
}
