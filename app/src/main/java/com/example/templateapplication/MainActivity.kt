package com.example.templateapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.templateapplication.network.ConnectionUtility
import com.example.templateapplication.ui.theme.BlancheTheme

class MainActivity : ComponentActivity() {

    lateinit var connectionLiveData: ConnectionUtility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = ConnectionUtility(this)

        setContent {
            val isOnline = connectionLiveData.observeAsState(false).value
            BlancheTheme(isOnline = isOnline) {
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
    BlancheTheme(darkTheme = false, isOnline = true) {
        BlancheApp()
    }
}

@Preview
@Composable
fun AppPreviewDarkMode() {
    BlancheTheme(darkTheme = true, isOnline = true) {
        BlancheApp()
    }
}
