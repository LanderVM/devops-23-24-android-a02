package com.example.templateapplication.ui.screens.formulepage.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import com.example.templateapplication.model.formules.Formule
import com.example.templateapplication.ui.theme.MainLightestColor

@Composable
fun DetailsFormuleCard(
    formule: Formule,
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
            text = formule.title,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = textColor,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(10.dp)
        )
        Divider(
            color = textColor,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(10.dp)
                .align(CenterHorizontally)
        )
        formule.listOfProperties.forEach { item ->
            Text(
                text = "✔  $item",
                color = textColor,
                modifier = Modifier
                    .padding(10.dp)
                    .align(CenterHorizontally)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Preview
@Composable
fun DetailsFormuleCardPreview() {
    val mockList = listOf(
        "lorem ipsum",
        "lorem ipsum",
        "lorem ipsum"
    )
    val backgroundColor = MainLightestColor
    val textColor = Color.Black
    val formule = Formule("Titel", mockList)

    DetailsFormuleCard(formule, backgroundColor, textColor)
}