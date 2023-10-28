package com.example.templateapplication.ui.screens.extraspage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.ui.theme.MainColor
import com.example.templateapplication.ui.theme.MainLightestColor


@Composable
fun ExtrasScreen (
    modifier: Modifier = Modifier
) {

    val scrollState = rememberScrollState()

    Column (
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth()
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(35.dp))
        HeadOfPage()
        //Text(text = "Contact gegevens",textAlign = TextAlign.Center,fontSize = 20.sp)
    }
}

@Composable
fun HeadOfPage(
    modifier: Modifier = Modifier
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 20.dp),
            color = MainColor,
            progress = 0.75f,
            trackColor = MainLightestColor,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text="Extra Materiaall",
            color = MainColor,
            modifier = Modifier
                .fillMaxWidth()
                .height(17.dp).padding(horizontal = 30.dp)
        )
    }
}