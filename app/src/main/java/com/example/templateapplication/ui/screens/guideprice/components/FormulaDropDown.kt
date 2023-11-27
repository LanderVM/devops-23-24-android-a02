package com.example.templateapplication.ui.screens.guideprice.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.templateapplication.R
import com.example.templateapplication.model.guidePriceEstimation.EstimationFormula

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun FormulaDropDownSelect(
    isExpanded: Boolean,
    setExpanded: (Boolean) -> Unit,
    options: List<EstimationFormula>,
    selectedOption: Int,
    setSelectedOption: (Int) -> Unit,
) {
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { setExpanded(!isExpanded) },
    ) {
        OutlinedTextField(
            readOnly = true,
            value = if (options.isEmpty()) "" else options[selectedOption].title,
            onValueChange = { },
            label = {
                Text(
                    text = "Formule",
                    color = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter)))
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = isExpanded
                )
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(0.5f),
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                setExpanded(false)
            }
        ) {
            options.forEachIndexed { _, s ->
                DropdownMenuItem(text = { Text(s.title) }, onClick = {
                    setSelectedOption(s.id)
                    setExpanded(!isExpanded)
                })
            }
        }
    }
}