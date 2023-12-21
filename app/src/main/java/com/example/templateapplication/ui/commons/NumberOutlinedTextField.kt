package com.example.templateapplication.ui.commons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

/**
 * Composable function for rendering an outlined text field specifically for number input.
 *
 * This function creates an outlined text field optimized for number input. It displays a label,
 * and handles the value as an integer. It provides a keyboard controller for better user experience.
 *
 * @param label The label text for the text field.
 * @param value The integer value to be displayed in the text field.
 * @param onValueChange Callback function to handle changes in the text field value.
 * @param keyboardController Optional software keyboard controller for showing or hiding the keyboard.
 */
@Composable
fun NumberOutlinedTextField(
    label: String,
    value: Int,
    onValueChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
) {
    OutlinedTextField(
        value = if (value == 0) "" else value.toString(),
        onValueChange = { onValueChange(it) },
        label = {
            Text(
                text = label,
                color = Color(0xFFe9dcc5)
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }
        ),
        modifier = Modifier
            .clickable { keyboardController?.show() },
    )
}