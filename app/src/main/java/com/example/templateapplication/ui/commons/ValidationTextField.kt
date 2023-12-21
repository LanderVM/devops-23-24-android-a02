package com.example.templateapplication.ui.commons
import android.content.Context
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.templateapplication.R
import com.example.templateapplication.model.UiText
import com.example.templateapplication.ui.utils.ReplyNavigationType

/**
 * Composable function for a validation text field with customizable features.
 *
 * This function creates an outlined text field that supports validation and error handling.
 * It provides various customization options like keyboard type, IME action, error message,
 * visibility toggle, and leading icon.
 *
 * @param modifier Modifier to be applied to the text field for customization.
 * @param placeholder Text to be displayed when the text field is empty.
 * @param text Current text value of the text field.
 * @param onValueChange Callback function for text value change events.
 * @param keyboardType Type of keyboard to be used for input.
 * @param imeAction IME action for the keyboard.
 * @param errorMessage Message to be displayed in case of an error.
 * @param isError Boolean flag indicating whether an error state should be shown.
 * @param isVisible Boolean flag for visibility control, particularly useful for password fields.
 * @param leadingIcon Optional leading icon to be displayed inside the text field.
 * @param singleLine Boolean flag to set the text field as a single line input.
 * @param maxLines Maximum number of lines for the text field.
 */
@Composable
fun ValidationTextFieldApp(
    modifier: Modifier = Modifier,
    placeholder: String,
    text: String = "",
    onValueChange: (String) -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    errorMessage: UiText? = null,
    isError: Boolean = false,
    isVisible: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    maxLines: Int = 1
) {
    val isKeyboardTypeNumber = keyboardType == KeyboardType.Phone || keyboardType == KeyboardType.Number
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val displayError = isError && errorMessage != null

    val colorBorder = when {
        isError -> MaterialTheme.colorScheme.error
        isFocused -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
    }

    Column(modifier = Modifier.width(300.dp)) {
        val context = LocalContext.current
        OutlinedTextField(
            label = { Text(text = placeholder) },
            value = text,
            onValueChange = { newInput ->
                onValueChange(
                    if (isKeyboardTypeNumber) newInput.filter { it.isDigit() || it == '+' }
                    else newInput
                )
            },
            maxLines = maxLines,
            singleLine = singleLine,
            interactionSource = interactionSource,
            visualTransformation = when {
                keyboardType == KeyboardType.Password && !isVisible -> PasswordVisualTransformation()
                else -> VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            isError = displayError,
            trailingIcon = {
                if (text.isNotEmpty()) {
                    ClearTextIconButton(onValueChange)
                }
            }
        )
        ErrorMessage(errorMessage, displayError, context)
    }
}

/**
 * Composable function for rendering a clear text icon inside a text field.
 *
 * Renders an icon button that, when clicked, clears the current text in the text field.
 *
 * @param onValueChange Callback function to be executed when the clear icon is clicked.
 */
@Composable
private fun ClearTextIconButton(onValueChange: (String) -> Unit) {
    IconButton(onClick = { onValueChange("") }) {
        Icon(Icons.Default.Clear, contentDescription = stringResource(id = R.string.clearableOutlinedTextField_iconDescription))
    }
}

/**
 * Composable function for displaying an error message.
 *
 * Renders a text component to display error messages. The visibility of the message is controlled
 * by a boolean flag.
 *
 * @param errorMessage The error message to display.
 * @param displayError Boolean flag indicating whether to display the error message.
 * @param context Current context, used for accessing resources.
 */
@Composable
private fun ErrorMessage(errorMessage: UiText?, displayError: Boolean, context: Context) {
    if (displayError) {
        Text(
            text = errorMessage!!.asString(context),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

