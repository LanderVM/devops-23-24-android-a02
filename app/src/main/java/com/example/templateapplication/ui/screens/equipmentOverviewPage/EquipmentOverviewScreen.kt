package com.example.templateapplication.ui.screens.equipmentOverviewPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.templateapplication.R
import com.example.templateapplication.model.common.quotation.Equipment
import com.example.templateapplication.model.extraMateriaal.EquipmentApiState
import com.example.templateapplication.ui.theme.MainLightestColor
import com.example.templateapplication.ui.utils.ReplyNavigationType

@ExperimentalMaterial3Api
@Composable
fun EquipmentOverviewScreen(
    navigationType: ReplyNavigationType,
    equipmentOverviewViewModel: EquipmentOverviewViewModel = viewModel(),
) {

    var selectedIndex by remember { mutableIntStateOf(0) }
    val list by equipmentOverviewViewModel.equipmentDbList.collectAsState()

    val options = listOf(
        stringResource(id = R.string.extraMaterial_sort_price_desc),
        stringResource(id = R.string.extraMaterial_sort_price_asc),
        stringResource(id = R.string.extraMaterial_sort_name_desc),
        stringResource(id = R.string.extraMaterial_sort_name_asc)
    )

    val columns: Int = when (navigationType) {
        ReplyNavigationType.NAVIGATION_RAIL -> {
            2
        }

        ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            4
        }

        else -> {
            1
        }
    }

    when (val apiState = equipmentOverviewViewModel.extraMaterialApiState) {
        is EquipmentApiState.Loading -> Text(stringResource(id = R.string.loading))
        is EquipmentApiState.Error -> {
            Text(apiState.errorMessage)
        }

        is EquipmentApiState.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth(),

                ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    SingleChoiceSegmentedButtonRow(modifier = Modifier.padding(top = 20.dp)) {
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
                items(equipmentOverviewViewModel.getListSorted(selectedIndex)) { extraItem ->
                    ExtraItemCard(
                        extraItem = extraItem,
                    )
                }
            }
        }
    }
}

@Composable
fun ExtraItemCard(
    extraItem: Equipment,
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
                    .scale(Scale.FILL)
                    .build(),
                placeholder = painterResource(id = R.drawable.foto7),
                contentDescription = extraItem.imgTxt,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(10.dp))
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
                    text = "€${extraItem.price}",
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
            }
        }
    }
}




