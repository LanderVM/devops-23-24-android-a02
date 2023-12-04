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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.model.formules.Formula
import com.example.templateapplication.ui.theme.MainLightestColor

@Composable
fun FormulaDetailsCard(
    formula: Formula,
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black,
) {
    Card(
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .shadow(elevation = 8.dp)

    ) {
        Text(
            text = formula.title,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
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
        formula.listOfProperties.forEach { item ->
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .align(CenterHorizontally)
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

@Preview
@Composable
fun DetailsFormulaCardPreview() {
    val mockList = listOf(
        "lorem ipsum",
        "lorem ipsum",
        "lorem ipsum"
    )
    val backgroundColor = MainLightestColor
    val textColor = Color.Black
    val formula = Formula("Titel", mockList)

    FormulaDetailsCard(formula, backgroundColor, textColor)
}