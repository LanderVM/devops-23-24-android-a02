package com.example.templateapplication.ui.screens.extraspage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.R
import com.example.templateapplication.ui.commons.ProgressieBar
import com.example.templateapplication.ui.theme.MainColor
import com.example.templateapplication.ui.theme.MainLightestColor

@ExperimentalMaterial3Api
@Composable
fun ExtrasScreen(
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    var selectedIndex by remember { mutableStateOf(0) }
    val options = listOf("Prijs asc", "Prijs desc", "Naam asc", "Naam desc")

    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth()
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HeadOfPage()
        Text(text = "Extra materiaal", textAlign = TextAlign.Center, fontSize = 25.sp)
        Spacer(modifier = Modifier.height(30.dp))

        SingleChoiceSegmentedButtonRow {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                    onClick = { selectedIndex = index },
                    selected = index == selectedIndex,
                    colors = SegmentedButtonColors(
                        activeContainerColor = Color(
                            android.graphics.Color.parseColor(
                                stringResource(
                                    R.string.lichterder,
                                ),
                            ),
                        ),
                        activeBorderColor = Color.Black,
                        activeContentColor = Color.Black,
                        disabledActiveBorderColor = Color.Black,
                        disabledActiveContainerColor = Color.White,
                        disabledActiveContentColor = Color.Black,
                        disabledInactiveBorderColor = Color.Black,
                        disabledInactiveContainerColor = Color.Black,
                        disabledInactiveContentColor = Color.Black,
                        inactiveBorderColor = Color.Black,
                        inactiveContainerColor = Color.White,
                        inactiveContentColor = Color.Black,
                    ),
                ) {
                    Text(text = label, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun HeadOfPage(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProgressieBar(
            text = "Extra Materiaal",
            progressie = 0.75f,
        )
    }
}
