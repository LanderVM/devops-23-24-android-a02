package com.example.templateapplication.ui.screens.extraspage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.R
import com.example.templateapplication.model.extraMateriaal.ExtraItemState
import com.example.templateapplication.model.extraMateriaal.ExtraItemViewModel
import com.example.templateapplication.model.extraMateriaal.ExtraItemDetailsApiState
import com.example.templateapplication.ui.commons.ProgressieBar
import com.example.templateapplication.ui.commons.NextPageButton
import com.example.templateapplication.ui.theme.MainColor
import com.example.templateapplication.ui.theme.MainLightestColor

@ExperimentalMaterial3Api
@Composable
fun ExtrasScreen(
    modifier: Modifier = Modifier,
    extraItemViewModel: ExtraItemViewModel = viewModel(factory = ExtraItemViewModel.Factory),
    navigateSamenvatting: () -> Unit,
    isOverview: Boolean, )
{


    var selectedIndex by remember { mutableStateOf(0) }
    val options = listOf("Prijs ↓", "Prijs ↑", "Naam ↓", "Naam ↑")

    val extraItemsListState by extraItemViewModel.extraItemListState.collectAsState()
    val extraMateriaalApiState = extraItemViewModel.extraMateriaalApiState

    val extraItemState by extraItemViewModel.extraItemState.collectAsState()


    when(extraMateriaalApiState){
        is ExtraItemDetailsApiState.Loading -> Text("Loading...")
        is ExtraItemDetailsApiState.Error -> {
            val errorMessage = extraMateriaalApiState.errorMessage
            Text("Couldn't load..., because of $errorMessage")}
        is ExtraItemDetailsApiState.Success -> {

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth()
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item{
            HeadOfPage()
        }
        item{
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
                        Text(text = label, fontSize = 15.sp)
                    }
                }
            }
        }


        items(extraItemViewModel.getListSorted(selectedIndex)){ extraItem ->
            ExtraItemCard(
                extraItem = extraItem,
                onAmountChanged = {extraItem, amount ->
                    extraItemViewModel.changeExtraItemAmount(extraItem, amount)},
                onAddItem= {extraItem -> extraItemViewModel.addItemToCart(extraItem)},
                onRemoveItem= {extraItem -> extraItemViewModel.removeItemFromCart(extraItem)},
                modifier = Modifier.padding(8.dp),
                isOverview = isOverview
            )
        }


        if(true /*!isOverview*/){
            item{
            NextPageButton(
                navigeer = navigateSamenvatting,
                enabled = true,
            )
        }}



    }}



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
            progression = 0.75f,
        )
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExtraItemCard(
    extraItem: ExtraItemState,
    onAmountChanged: (ExtraItemState, Int) -> Unit,
    onAddItem: (ExtraItemState) -> Unit,
    onRemoveItem: (ExtraItemState) -> Unit,
    modifier: Modifier = Modifier,
    isOverview: Boolean) {

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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Image
            Image(
                painter = painterResource(id = R.drawable.foto7), // Replace with actual image
                contentDescription = null, // Content description can be set based on your use case
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Item Details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Title
                Text(
                    modifier = Modifier.size(170.dp, 40.dp),
                    text = "${extraItem.title}",
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                // Price
                Text(
                    text = "$${extraItem.price}",
                    style = MaterialTheme.typography.headlineSmall,

                )
            }
            Text(
                text = buildAnnotatedString {
                    extraItem.attributes.forEachIndexed { index, attribute ->
                        append(attribute)
                        if (index < extraItem.attributes.size - 1) {
                            // Append a new line if it's not the last item
                            append("\n")
                        }
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){


            Text(
                text = "${extraItem.stock} in stock",
                style = MaterialTheme.typography.bodyLarge,

            )
            Spacer(modifier = Modifier.height(16.dp))
            if(true /*!isOverView*/){
                    if (extraItem.isEditing) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = extraItem.amount.takeIf { it != 0 }?.toString() ?: "1",
                                onValueChange = {
                                    val enteredAmount = it.toIntOrNull()
                                    extraItem.amount = when {
                                        enteredAmount != null && enteredAmount > 0 -> enteredAmount.coerceAtMost(extraItem.stock)
                                        else ->1
                                    }
                                    Log.i("amount", enteredAmount.toString())
                                    onAddItem(extraItem)

                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number
                                ),
                                label = { Text(text = "Aantal", fontSize = 10.sp) },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    disabledContainerColor = Color.White,
                                ),
                                modifier = Modifier
                                    .width(70.dp)

                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            IconButton(
                                onClick = {
                                    extraItem.isEditing = false
                                    onRemoveItem(extraItem)
                                },
                                colors = IconButtonColors(containerColor = Color.Transparent, contentColor = Color.Red, disabledContentColor = Color.Transparent, disabledContainerColor = Color.Red)

                            ) {
                                Icon(Icons.Filled.Delete, "Delete Button", modifier=Modifier.size(35.dp))
                            }
                        }
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,

                        ) {
                            Button(
                                onClick = {
                                    extraItem.isEditing = true
                                    onAddItem(extraItem)
                                },
                                colors = ButtonColors(containerColor = MainColor, contentColor = Color.White, disabledContentColor = Color.White, disabledContainerColor = MainColor)
                            ) {
                                Text(text = "Voeg Toe")
                            }
                        }
                    }
                }
            Spacer(modifier = Modifier.height(16.dp))
            }
        }}
    }




