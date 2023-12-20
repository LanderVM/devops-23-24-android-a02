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