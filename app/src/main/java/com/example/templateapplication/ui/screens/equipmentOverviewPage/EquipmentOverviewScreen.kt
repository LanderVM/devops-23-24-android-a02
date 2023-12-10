package com.example.templateapplication.ui.screens.equipmentOverviewPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.R
import com.example.templateapplication.model.common.quotation.Equipment
import com.example.templateapplication.ui.theme.MainLightestColor

@ExperimentalMaterial3Api
@Composable
fun EquipmentOverviewScreen(
    modifier: Modifier = Modifier,
    equipmentOverviewViewModel: EquipmentOverviewViewModel = viewModel(),
) {

    var selectedIndex by remember { mutableIntStateOf(0) }

    val options = listOf(
        stringResource(id = R.string.extraMaterial_sort_price_desc),
        stringResource(id = R.string.extraMaterial_sort_price_asc),
        stringResource(id = R.string.extraMaterial_sort_name_desc),
        stringResource(id = R.string.extraMaterial_sort_name_asc)
    )

//    when (val extraEquipmentApiState = equipmentOverviewViewModel.extraMateriaalApiState) { FIXME
//        is ExtraItemDetailsApiState.Loading -> Text(stringResource(id = R.string.loading))
//        is ExtraItemDetailsApiState.Error -> {
//            val errorMessage = extraEquipmentApiState.errorMessage
//            Text(stringResource(id = R.string.error, errorMessage))
//        }
//        is ExtraItemDetailsApiState.Success -> {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            SingleChoiceSegmentedButtonRow {
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
                modifier = Modifier.padding(8.dp),
                extraItem = extraItem,
            )
        }
    }
}
//    }
//}

@Composable
fun ExtraItemCard(
    modifier: Modifier = Modifier,
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
                    text = extraItem.title,
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${extraItem.stock} in stock",
                    style = MaterialTheme.typography.bodyLarge,

                    )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}




