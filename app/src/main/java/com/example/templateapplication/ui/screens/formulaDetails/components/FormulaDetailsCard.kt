package com.example.templateapplication.ui.screens.formulaDetails.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.model.common.quotation.Formula
import com.example.templateapplication.ui.utils.ReplyNavigationType

/**
 * Composable function for displaying a card with details of a formula.
 *
 * This function creates a card that presents information about a formula. The card's appearance,
 * including its size and font size, varies depending on the navigation type. The card displays
 * the formula's title and its attributes.
 *
 * @param navigationType The type of navigation being used in the UI, affecting the card's size.
 * @param formula The formula data to be displayed in the card.
 * @param backgroundColor The background color of the card. Default is white.
 * @param textColor The color of the text inside the card. Default is black.
 */
@Composable
fun FormulaDetailsCard(
    navigationType: ReplyNavigationType,
    formula: Formula,
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black,
) {
    val cardHeight : Dp
    val fontSize : TextUnit
    when (navigationType) {
        ReplyNavigationType.NAVIGATION_RAIL -> {
            cardHeight= 600.dp
            fontSize = 30.sp

        }
        ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            cardHeight= 650.dp
            fontSize = 45.sp
        }
        else -> {
            cardHeight= 500.dp
            fontSize = 30.sp

        }
    }
    Card(
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
        ),
        modifier = Modifier
            .height(cardHeight)
            .fillMaxWidth()
            .padding(20.dp)
            .shadow(elevation = 8.dp)

    ) {
        Text(
            text = formula.title,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize,
            color = textColor,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(10.dp)
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(10.dp)
                .align(CenterHorizontally),
            color = textColor
        )
        formula.attributes.forEach { item ->
            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .align(CenterHorizontally)
                    .fillMaxWidth(0.4f)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = item,
                    color = textColor,
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}