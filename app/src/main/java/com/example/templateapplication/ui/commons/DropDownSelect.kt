package com.example.templateapplication.ui.commons

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

data class DropDownOption(val title: String, val id: Int)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DropDownSelect(
    label: String,
    isExpanded: Boolean,
    setExpanded: (Boolean) -> Unit,
    dropDownOptions: List<DropDownOption>,
    selectedOption: Int,
    setSelectedOption: (Int) -> Unit,
) {
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { setExpanded(!isExpanded) },
    ) {
        OutlinedTextField(
            readOnly = true,
            value = if (dropDownOptions.isEmpty()) "" else dropDownOptions[selectedOption].title,
            onValueChange = { },
            label = {
                Text(
                    text = label,
                    color = Color(0xFFe9dcc5)
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
            dropDownOptions.forEachIndexed { _, s ->
                DropdownMenuItem(text = { Text(s.title) }, onClick = {
                    setSelectedOption(s.id)
                    setExpanded(!isExpanded)
                })
            }
        }
    }
}