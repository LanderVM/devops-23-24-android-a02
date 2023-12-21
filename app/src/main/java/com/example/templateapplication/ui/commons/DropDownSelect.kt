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

/**
 * Data class representing an option in a dropdown menu.
 *
 * @param title The display text of the dropdown option.
 * @param id The unique identifier for the dropdown option.
 */
data class DropDownOption(val title: String, val id: Int)

/**
 * Composable function for rendering a dropdown selection menu.
 *
 * This function creates a dropdown menu with given options. It allows the user to select an option
 * from the menu, displaying the selected option in an outlined text field.
 *
 * @param label The label for the dropdown menu.
 * @param isExpanded Boolean indicating if the dropdown is currently expanded.
 * @param setExpanded Function to change the expanded state of the dropdown.
 * @param dropDownOptions List of DropDownOption, each representing an option in the dropdown.
 * @param selectedOption The ID of the currently selected option.
 * @param setSelectedOption Function to update the selected option.
 */
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