package com.example.templateapplication.ui.screens.extraspage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.R
import com.example.templateapplication.data.Datasource
import com.example.templateapplication.model.extraMateriaal.ExtraMateriaalItem
import com.example.templateapplication.ui.commons.ProgressieBar
import com.example.templateapplication.ui.commons.Titel
import com.example.templateapplication.ui.theme.MainLightestColor

@ExperimentalMaterial3Api
@Composable
fun ExtrasScreen(
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    var selectedIndex by remember { mutableStateOf(0) }
    val options = listOf("Prijs asc", "Prijs desc", "Naam asc", "Naam desc")

    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HeadOfPage()
        Titel(
        text = "Extra Materiaal",)
        Spacer(modifier = Modifier.height(30.dp))

        SingleChoiceSegmentedButtonRow {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                    onClick = { selectedIndex = index },
                    selected = index == selectedIndex,
                    colors = SegmentedButtonColors(
                        activeContainerColor = Color(
                            android.graphics.Color.parseColor(
                                stringResource(
                                    R.string.lichterder,
                                ),
                            ),
                        ),
                        activeBorderColor = Color.Black,
                        activeContentColor = Color.Black,
                        disabledActiveBorderColor = Color.Black,
                        disabledActiveContainerColor = Color.White,
                        disabledActiveContentColor = Color.Black,
                        disabledInactiveBorderColor = Color.Black,
                        disabledInactiveContainerColor = Color.Black,
                        disabledInactiveContentColor = Color.Black,
                        inactiveBorderColor = Color.Black,
                        inactiveContainerColor = Color.White,
                        inactiveContentColor = Color.Black,
                    ),
                ) {
                    Text(text = label, fontSize = 12.sp)
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        ExtraItemList(extraList = Datasource().loadExtraItems())
    }
}

@Composable
fun HeadOfPage(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProgressieBar(
            text = "Extra Materiaal",
            progressie = 0.75f,
        )
    }
}


@Composable
fun ExtraItemList(extraList: List<ExtraMateriaalItem>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(extraList) { extraItem ->
            ExtraItemCard(
                extraItem = extraItem,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExtraItemCard(extraItem: ExtraMateriaalItem, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        shape = RoundedCornerShape(8.dp),
        colors = CardColors(containerColor = MainLightestColor, contentColor = Color.Black, disabledContainerColor = Color.Gray, disabledContentColor = Color.Black)
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(16.dp)
        ) {
            // ExtraItem Details
            Column {
                Text(text = extraItem.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = extraItem.category, fontSize = 16.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "€${extraItem.price}", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                // Adjustable amount
                var amount by remember { mutableStateOf(extraItem.amount) }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    TextField(
                        value = amount.toString(),
                        onValueChange = {
                            if (it.isNotBlank()) {
                                amount = it.toInt()
                            } else {
                                amount = 0
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        label = { Text("Aantal") },
                        colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                        modifier = Modifier
                            .width(90.dp)

                    )
                }
            }
            Spacer(modifier = Modifier.width(50.dp))
            // ExtraItem Image
            Image(
                painter = painterResource(id = extraItem.imageResourceId),
                contentDescription = null, // Provide a meaningful description for accessibility
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(4.dp)) // Adjust corner radius as needed
            )


        }
    }
}


@Preview
@Composable
private fun ExtraItemCardPreview() {
    ExtraItemCard(ExtraMateriaalItem("Stoel", "stoelen", 2.90, 5, R.drawable.foto7))
}

@Preview
@Composable
private fun ExtraItemListPreview() {
     ExtraItemList(extraList = Datasource().loadExtraItems() )
}
