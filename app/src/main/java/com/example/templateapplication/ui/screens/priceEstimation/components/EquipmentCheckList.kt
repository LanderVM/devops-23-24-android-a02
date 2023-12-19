package com.example.templateapplication.ui.screens.priceEstimation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.templateapplication.model.guidePriceEstimation.EstimationEquipment
import com.example.templateapplication.ui.theme.md_theme_light_onSecondary
import com.example.templateapplication.ui.theme.md_theme_light_secondary
import com.example.templateapplication.ui.theme.md_theme_light_tertiary

@Composable
fun EquipmentCheckList(
    list: List<EstimationEquipment>,
    hasChecked: (EstimationEquipment) -> Boolean,
    onCheck: (EstimationEquipment) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 85.dp)
            .wrapContentSize(Alignment.Center)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            list.forEachIndexed { _, equipment ->
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Checkbox(
                        checked = hasChecked(equipment),
                        onCheckedChange = { onCheck(equipment) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = md_theme_light_secondary,
                            checkmarkColor = md_theme_light_onSecondary,
                            uncheckedColor = md_theme_light_tertiary,
                        ),
                    )
                    Text(
                        text = equipment.title,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        }
    }
}