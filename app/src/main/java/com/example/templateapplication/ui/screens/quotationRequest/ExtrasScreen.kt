package com.example.templateapplication.ui.screens.quotationRequest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.templateapplication.R
import com.example.templateapplication.model.ApiResponse
import com.example.templateapplication.model.quotationRequest.ExtraItemState
import com.example.templateapplication.ui.commons.NextPageButton
import com.example.templateapplication.ui.theme.MainColor
import com.example.templateapplication.ui.theme.MainLightestColor
import com.example.templateapplication.ui.utils.ReplyNavigationType

@ExperimentalMaterial3Api
@Composable
fun ExtrasScreen(
    navigationType: ReplyNavigationType,
    quotationRequestViewModel: QuotationRequestViewModel = viewModel(),
    navigateSummary: () -> Unit,
    isOverview: Boolean,
) {

    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf(
        stringResource(id = R.string.extraMaterial_sort_price_desc),
        stringResource(id = R.string.extraMaterial_sort_price_asc),
        stringResource(id = R.string.extraMaterial_sort_name_desc),
        stringResource(id = R.string.extraMaterial_sort_name_asc)
    )

    when (quotationRequestViewModel.extraMateriaalApiState) {
        is ApiResponse.Loading -> Text(stringResource(id = R.string.loading))
        is ApiResponse.Error -> {
            Text(stringResource(id = R.string.error))
        }

        is ApiResponse.Success -> {
            val columns: Int
            val paddingSegment: Dp
            val paddingButton: Dp
            when (navigationType) {
                ReplyNavigationType.NAVIGATION_RAIL -> {
                    columns = 2
                    paddingSegment = 60.dp
                    paddingButton = 100.dp
                }

                ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
                    columns = 4
                    paddingSegment = 100.dp
                    paddingButton = 170.dp
                }

                else -> {
                    columns = 1
                    paddingSegment = 0.dp
                    paddingButton = 40.dp
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                modifier = Modifier
                    .padding(end = 30.dp, start = 30.dp, bottom = 30.dp)
                    .fillMaxWidth(),
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    HeadOfPage()
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier.padding(
                            vertical = 20.dp,
                            horizontal = paddingSegment
                        )
                    ) {
                        options.forEachIndexed { index, label ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = options.size
                                ),
                                onClick = { selectedIndex = index },
                                selected = index == selectedIndex,
                                colors = SegmentedButtonColors(
                                    activeContainerColor = Color(0xFFe9dcc5),
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
                items(quotationRequestViewModel.getListSorted(selectedIndex)) { extraItem ->
                    ExtraItemCard(
                        extraItem = extraItem,
                        onAddItem = { quotationRequestViewModel.addItemToCart(extraItem) },
                        onRemoveItem = { quotationRequestViewModel.removeItemFromCart(extraItem) },
                        isOverview = isOverview
                    )
                }

                if (!isOverview) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        NextPageButton(
                            modifier = Modifier.padding(horizontal = paddingButton),
                            navigate = navigateSummary,
                            enabled = true,
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ExtraItemCard(
    extraItem: ExtraItemState,
    onAddItem: (ExtraItemState) -> Unit,
    onRemoveItem: (ExtraItemState) -> Unit,
    isOverview: Boolean
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        shape = RoundedCornerShape(8.dp),
        colors = CardColors(
            containerColor = MainLightestColor,
            contentColor = Color.Black,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(extraItem.imgUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.foto7),
                contentDescription = extraItem.imgTxt,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    fontSize = 20.sp,
                    modifier = Modifier.size(170.dp, 70.dp),
                    text = extraItem.title,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Visible
                )

                Text(
                    fontSize = 20.sp,
                    text = "${stringResource(id = R.string.price_icon)}${extraItem.price}",
                    style = MaterialTheme.typography.headlineSmall,

                    )
            }
            Text(
                text = buildAnnotatedString {
                    extraItem.attributes.forEachIndexed { index, attribute ->
                        append(attribute)
                        if (index < extraItem.attributes.size - 1) {
                            append("\n")
                        }
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.height(65.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {


                Text(
                    text = "${extraItem.stock} ${stringResource(id = R.string.extraMaterial_in_stock)}",
                    style = MaterialTheme.typography.bodyLarge,
                    )
                Spacer(modifier = Modifier.height(16.dp))
                if (!isOverview) {
                    if (extraItem.isEditing) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = extraItem.amount.takeIf { it != 0 }?.toString() ?: "",
                                singleLine = true,
                                onValueChange = {
                                    val enteredAmount = it.toIntOrNull()
                                    extraItem.amount = when {
                                        enteredAmount != null && enteredAmount > 0 -> enteredAmount.coerceAtMost(
                                            extraItem.stock
                                        )

                                        else -> 0
                                    }
                                    onAddItem(extraItem)

                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                                ),
                                label = { Text(text = stringResource(id = R.string.extraMaterial_number_of), fontSize = 10.sp) },
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
                                colors = IconButtonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = Color.Red,
                                    disabledContentColor = Color.Transparent,
                                    disabledContainerColor = Color.Red
                                )

                            ) {
                                Icon(
                                    Icons.Filled.Delete,
                                    stringResource(id = R.string.extraMaterial_delete_button),
                                    modifier = Modifier.size(35.dp)
                                )
                            }
                        }
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            ) {
                            Button(
                                onClick = {
                                    extraItem.amount = 1
                                    onAddItem(extraItem)
                                    extraItem.isEditing = true

                                },
                                colors = ButtonColors(
                                    containerColor = MainColor,
                                    contentColor = Color.White,
                                    disabledContentColor = Color.White,
                                    disabledContainerColor = MainColor
                                )
                            ) {
                                Text(text = stringResource(id = R.string.add))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}




