package com.example.templateapplication.ui.screens.priceEstimation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.templateapplication.R
import com.example.templateapplication.ui.theme.md_theme_light_onSecondary
import com.example.templateapplication.ui.theme.md_theme_light_secondary
import com.example.templateapplication.ui.theme.md_theme_light_tertiary

/**
 * Composable function for displaying a checkbox option for selecting Tripel beer.
 *
 * This function creates a checkbox with a label specifically for the option of including Tripel beer.
 * The checkbox is only active and displayed based on the selected formula. The state and behavior of
 * the checkbox are controlled by the provided Boolean state and callback function.
 *
 * @param selectedFormula The ID of the currently selected formula, which determines if the checkbox is shown.
 * @param hasCheck Boolean state indicating if the checkbox is checked.
 * @param onCheck Callback function to be executed when the checkbox is checked/unchecked.
 */
@Composable
fun WantsTripelBeerCheckbox(
    selectedFormula: Int,
    hasCheck: Boolean,
    onCheck: (Boolean) -> Unit,
) {
    if (selectedFormula == 1) onCheck(false)
    else
        Row(
            modifier = Modifier.height(50.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.guidePrice_formula_tripleBeer),
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Checkbox(
                checked = hasCheck,
                onCheckedChange = { onCheck(!hasCheck) },
                colors = CheckboxDefaults.colors(
                    checkedColor = md_theme_light_secondary,
                    checkmarkColor = md_theme_light_onSecondary,
                    uncheckedColor = md_theme_light_tertiary,
                ),
            )
        }
}