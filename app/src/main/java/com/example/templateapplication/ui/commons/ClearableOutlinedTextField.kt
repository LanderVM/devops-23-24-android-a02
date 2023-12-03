package com.example.templateapplication.ui.commons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.templateapplication.R


@Composable
fun ClearableOutlinedTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        label = { Text(text = label) },
        value = value,
        onValueChange = onValueChange,
        trailingIcon = {
            IconButton(
                onClick = { onValueChange("") },
                enabled = value.isNotEmpty(),
            ) {
                AnimatedVisibility(
                    visible = value.isNotEmpty(),
                    enter = fadeIn()
                ) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = stringResource(id = R.string.clearableOutlinedTextField_iconDescription)
                    )
                }
            }
        },
    )
}