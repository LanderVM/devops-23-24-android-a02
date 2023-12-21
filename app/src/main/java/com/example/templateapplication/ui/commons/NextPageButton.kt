package com.example.templateapplication.ui.commons

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.templateapplication.R
import com.example.templateapplication.ui.theme.DisabledButtonColor
import com.example.templateapplication.ui.theme.MainColor

/**
 * Composable function for rendering a button that navigates to the next page.
 *
 * This function creates a button with a specific shape and color scheme, which can be
 * used to navigate to the next page or perform a similar action when clicked.
 *
 * @param modifier Modifier to be applied to the button for customization.
 * @param navigeer A lambda function to be executed when the button is clicked.
 * @param enabled Boolean indicating if the button is enabled or disabled.
 */
@Composable
fun NextPageButton(
    modifier: Modifier = Modifier,
    navigeer: () -> Unit,
    enabled: Boolean = true,
) {
    Button(
        onClick = navigeer,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MainColor,
            disabledContainerColor = DisabledButtonColor,
            contentColor = Color.White,
            disabledContentColor = Color.White,
        ),
        enabled = enabled,
        modifier = modifier,
    ) {
        Text(text = stringResource(id = R.string.nextPageButton_text))
    }
}

@Preview
@Composable
fun VolgendeKnopPreview(
    modifier: Modifier = Modifier,
) {
    NextPageButton(
        navigeer = {}
    )
}