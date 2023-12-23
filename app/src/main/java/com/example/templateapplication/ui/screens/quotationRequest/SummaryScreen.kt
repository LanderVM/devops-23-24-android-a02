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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.templateapplication.R
import com.example.templateapplication.model.quotationRequest.ExtraItemState
import com.example.templateapplication.model.quotationRequest.QuotationRequestState
import com.example.templateapplication.model.quotationRequest.QuotationUiState
import com.example.templateapplication.network.restApi.quotationRequest.ApiQuotationRequestPostApiState
import com.example.templateapplication.ui.commons.AlertPopUp
import com.example.templateapplication.ui.theme.DisabledButtonColor
import com.example.templateapplication.ui.theme.MainColor
import com.example.templateapplication.ui.theme.MainLighterColor
import com.example.templateapplication.ui.theme.MainLightestColor
import com.example.templateapplication.ui.utils.ReplyNavigationType


@Composable
fun SummaryScreen(
    navigationType: ReplyNavigationType,
    quotationRequestViewModel: QuotationRequestViewModel = viewModel(),
    navigateEventDetails: () -> Unit,
    navigateContactDetails: () -> Unit,
    navigateExtras: () -> Unit,
) {
    val requestState by quotationRequestViewModel.quotationRequestState.collectAsState()
    val uiState by quotationRequestViewModel.quotationUiState.collectAsState()
    var showConfirmationPop by remember { mutableStateOf(false) }
    val formulaList by quotationRequestViewModel.formulaState.collectAsState()

    val columns: Int
    val fontSizeCost: TextUnit
    when (navigationType) {
        ReplyNavigationType.NAVIGATION_RAIL -> {
            columns = 2
            fontSizeCost = 15.sp
        }

        ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            columns = 4
            fontSizeCost = 22.sp
        }

        else -> {
            columns = 1
            fontSizeCost = 10.sp
        }
    }
    var show by rememberSaveable { mutableStateOf(true) }
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),

        ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            HeadOfPage()
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            Navigation(
                navigateContactDetails = navigateContactDetails,
                navigateEventDetails = navigateEventDetails,
                navigateExtras = navigateExtras
            )
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 50.dp),
                thickness = 4.dp,
                color = Color.LightGray
            )
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            EventDetails(
                uiState = uiState,
                dateRange = quotationRequestViewModel.getDateRange()
            )
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 50.dp),
                thickness = 4.dp,
                color = Color.LightGray
            )
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            ContactDetails(
                requestState = requestState,
            )
        }
        if (quotationRequestViewModel.getListAddedItems().isNotEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 50.dp),
                    thickness = 4.dp,
                    color = Color.LightGray
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.extraItems_title),
                        textAlign = TextAlign.Start,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MainColor,
                    )
                    IconButton(onClick = { show = !show }) {
                        Icon(
                            Icons.Outlined.ArrowDropDown,
                            contentDescription = stringResource(id = R.string.summaryScreen_dropdown)
                        )
                    }
                }

            }
            if (show) {
                items(quotationRequestViewModel.getListAddedItems()) { extraItem ->
                    ExtraItemCard(
                        extraItem = extraItem,
                    )
                }
            }
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 15.dp, 50.dp),
                thickness = 4.dp,
                color = Color.LightGray
            )
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            CostDetails(
                requestState = requestState,
                fontSizeCost = fontSizeCost,
                quotationRequestViewModel = quotationRequestViewModel,
            )
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            when (val state = quotationRequestViewModel.postQuotationRequestApiState) {
                is ApiQuotationRequestPostApiState.Error -> Text(text = state.errorMessage)
                ApiQuotationRequestPostApiState.Idle -> {}
                ApiQuotationRequestPostApiState.Loading -> Text(text = stringResource(id = R.string.summaryData_loading))
                ApiQuotationRequestPostApiState.Success -> Text(text = stringResource(id = R.string.summaryData_confirmed))
            }
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            Button(
                modifier = Modifier.padding(vertical = 50.dp),
                onClick = {
                    showConfirmationPop = true
                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainColor,
                    disabledContainerColor = DisabledButtonColor,
                    contentColor = Color.White,
                    disabledContentColor = Color.White
                ),
            ) {
                Text(text = stringResource(id = R.string.summaryData_quoteRequest))
            }
        }
    }
    if (showConfirmationPop) {
        AlertPopUp(
            onDismissRequest = { showConfirmationPop = false },
            dialogTitle = stringResource(id = R.string.alert_title),
            dialogText = stringResource(id = R.string.alert_text),
            confirmText = stringResource(id = R.string.alert_confirmation),
            dismissText = stringResource(id = R.string.alert_dismiss),
            onConfirmation = {
                quotationRequestViewModel.sendQuotationRequest()
                showConfirmationPop = false
            }
        )
    }
}

@Composable
fun HeadOfPage(
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.summaryData_overview),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            color = MainColor,
        )
        LinearProgressIndicator(
            progress = { 1.00f },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            color = MainColor,
            trackColor = MainLightestColor,
        )
    }
}

@Composable
fun Navigation(
    navigateEventDetails: () -> Unit,
    navigateContactDetails: () -> Unit,
    navigateExtras: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.summaryData_navigation_goto),
            textAlign = TextAlign.Left,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            fontSize = 30.sp,
            color = Color.Black,
        )
        NavigationRow(
            text = stringResource(id = R.string.summaryData_navigation_event),
            navigateExtras = { navigateEventDetails() }
        )
        NavigationRow(
            text = stringResource(id = R.string.summaryData_navigation_contact),
            navigateExtras = { navigateContactDetails() }
        )
        NavigationRow(
            text = stringResource(id = R.string.summaryData_navigation_extraMaterial),
            navigateExtras = { navigateExtras() }
        )
    }
}

@Composable
fun NavigationRow(
    text: String,
    navigateExtras: () -> Unit
) {
    Spacer(modifier = Modifier.height(10.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 35.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.filled_circle),
            contentDescription = stringResource(id = R.string.summaryData_navigation_circle),
            modifier = Modifier.size(13.dp, 13.dp)
        )
        Spacer(modifier = Modifier.width(30.dp))
        ClickableText(
            text = AnnotatedString(text),
            onClick = { navigateExtras() },
            style = TextStyle(fontSize = 22.sp)
        )
    }
}

@Composable
fun EventDetails(
    uiState: QuotationUiState,
    dateRange: String,
) {

    var show by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.summaryData_eventDetails_title),
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MainColor,
            )
            IconButton(onClick = { show = !show }) {
                Icon(
                    Icons.Outlined.ArrowDropDown,
                    contentDescription = stringResource(id = R.string.summaryScreen_dropdown)
                )
            }
        }
        if (show) {
            Spacer(modifier = Modifier.height(20.dp))
            ListItem(
                headlineContent = {
                    Text(text = stringResource(id = R.string.summaryScreen_date), fontSize = 18.sp)
                },
                supportingContent = {
                    Text(
                        text = dateRange,
                        fontSize = 16.sp
                    )
                },
                colors = ListItemDefaults.colors(
                    containerColor = Color(0XFFD3B98B)
                ),
                modifier = Modifier.padding(horizontal = 30.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            ListItem(
                headlineContent = {
                    Text(
                        text = stringResource(id = R.string.summaryData_eventDetails_location),
                        fontSize = 18.sp
                    )
                },
                supportingContent = {
                    Text(
                        text = uiState.googleMaps.eventAddressAutocompleteCandidates.candidates[0].formattedAddress,
                        fontSize = 16.sp
                    )
                },
                colors = ListItemDefaults.colors(
                    containerColor = Color(0XFFD3B98B)
                ),
                modifier = Modifier.padding(horizontal = 30.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun ContactDetails(
    requestState: QuotationRequestState,
) {
    var show by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.summaryData_contactDetails_title),
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MainColor,
            )
            IconButton(onClick = { show = !show }) {
                Icon(
                    Icons.Outlined.ArrowDropDown,
                    contentDescription = stringResource(id = R.string.summaryScreen_dropdown)
                )
            }
        }
        if (show) {
            ListItemRow(
                headline = stringResource(id = R.string.summaryData_contactDetails_fullName),
                content = requestState.customer.firstName + " " + requestState.customer.lastName
            )
            ListItemRow(
                headline = stringResource(id = R.string.contactDetails_email),
                content = requestState.customer.email
            )
            ListItemRow(
                headline = stringResource(id = R.string.contactDetails_phone_number),
                content = requestState.customer.phoneNumber
            )
            ListItemRow(
                headline = stringResource(id = R.string.summaryData_contactDetails_adress),
                content = requestState.customer.billingAddress.street + " " + requestState.customer.billingAddress.houseNumber + ", " + requestState.customer.billingAddress.postalCode + " " + requestState.customer.billingAddress.city
            )

            if (requestState.customer.vatNumber.isNotBlank()) {
                Spacer(modifier = Modifier.height(20.dp))
                ListItemRow(
                    headline = stringResource(id = R.string.contactDetails_vat_number),
                    content = requestState.customer.vatNumber
                )
            }

        }


    }
}

@Composable
fun ListItemRow(headline: String, content: String) {
    ListItem(
        headlineContent = { Text(text = headline, fontSize = 18.sp) },
        supportingContent = { Text(text = content, fontSize = 16.sp) },
        colors = ListItemDefaults.colors(containerColor = Color(0XFFD3B98B)),
        modifier = Modifier.padding(horizontal = 30.dp)
    )
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun ExtraItemCard(
    extraItem: ExtraItemState,
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

            Text(
                modifier = Modifier.size(170.dp, 70.dp),
                text = extraItem.title,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${stringResource(id = R.string.price_icon)} ${extraItem.price} x ${extraItem.amount} ${
                    stringResource(
                        id = R.string.summaryScreen_pieces
                    )
                }",
                style = MaterialTheme.typography.bodyMedium,

                )

        }

    }
}

@Composable
fun CostDetails(
    requestState: QuotationRequestState,
    fontSizeCost: TextUnit,
    quotationRequestViewModel: QuotationRequestViewModel
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.summaryData_priceDetails_title),
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = MainColor,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = MainLighterColor,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(35.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.summaryData_priceDetails_description),
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth(0.4f),
                        fontSize = fontSizeCost,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = stringResource(id = R.string.summaryData_priceDetails_price),
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth(0.3f),
                        fontSize = fontSizeCost,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = stringResource(id = R.string.summaryData_priceDetails_subtotal),
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth(0.3f),
                        fontSize = fontSizeCost,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 2.dp,
                    color = Color.Gray
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(35.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = requestState.formula!!.title,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth(0.4f),
                        fontSize = fontSizeCost,
                        color = Color.Black,
                    )
                    Text(
                        text = "${stringResource(id = R.string.price_icon)} ${quotationRequestViewModel.getPriceBasicFormula()}",
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth(0.3f),
                        fontSize = fontSizeCost,
                        color = Color.Black,
                    )
                    Text(
                        text = "${stringResource(id = R.string.price_icon)} ${quotationRequestViewModel.getPriceBasicFormula()}",
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth(0.3f),
                        fontSize = fontSizeCost,
                        color = Color.Black,
                    )
                }
                if (requestState.formula!!.id == 2 || requestState.formula.id == 3) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(35.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "${stringResource(id = R.string.summaryScreen_beerIncluded)}  x ${requestState.numberOfPeople} ",
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(0.4f),
                            fontSize = fontSizeCost,
                            color = Color.Black,
                        )
                        Text(
                            text = "${stringResource(id = R.string.price_icon)} 3.0",
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(0.3f),
                            fontSize = fontSizeCost,
                            color = Color.Black,
                        )
                        Text(
                            text = "${stringResource(id = R.string.price_icon)} ${quotationRequestViewModel.calculatePriceBeer()}",
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(0.3f),
                            fontSize = fontSizeCost,
                            color = Color.Black,
                        )
                    }
                }
                if (requestState.formula.id == 3) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(35.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "${stringResource(id = R.string.summaryScreen_bbqIncluded)}  x ${requestState.numberOfPeople}",
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(0.4f),
                            fontSize = fontSizeCost,
                            color = Color.Black,
                        )
                        Text(
                            text = "${stringResource(id = R.string.price_icon)} 12.0",
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(0.3f),
                            fontSize = fontSizeCost,
                            color = Color.Black,
                        )
                        Text(
                            text = "${stringResource(id = R.string.price_icon)} ${quotationRequestViewModel.calculatePriceBbq()}",
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(0.3f),
                            fontSize = fontSizeCost,
                            color = Color.Black,
                        )
                    }
                }
                quotationRequestViewModel.getListAddedItems().forEach { extraItem ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(35.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "${extraItem.title} x ${extraItem.amount}",
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(0.4f),
                            fontSize = fontSizeCost,
                            color = Color.Black,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = "${stringResource(id = R.string.price_icon)} ${
                                String.format(
                                    "%.2f",
                                    extraItem.price
                                )
                            }",
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(0.3f),
                            fontSize = fontSizeCost,
                            color = Color.Black,
                        )
                        Text(
                            text = "${stringResource(id = R.string.price_icon)} ${
                                String.format(
                                    "%.2f",
                                    extraItem.price * extraItem.amount
                                )
                            }",
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(0.3f),
                            fontSize = fontSizeCost,
                            color = Color.Black,
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(35.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.summaryScreen_transportationCosts),
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth(0.4f),
                        fontSize = fontSizeCost,
                        color = Color.Black,
                    )
                    Text(
                        text = "${stringResource(id = R.string.price_icon)} 0.75",
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth(0.3f),
                        fontSize = fontSizeCost,
                        color = Color.Black,
                    )
                    Text(
                        text = "${stringResource(id = R.string.price_icon)} ${quotationRequestViewModel.calulateTransportCosts()}",
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth(0.3f),
                        fontSize = fontSizeCost,
                        color = Color.Black,
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 2.dp,
                    color = Color.Gray
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(35.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.summaryScreen_totalExclusiveBtw),
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth(0.6f),
                        fontSize = fontSizeCost,
                        color = Color.Black,
                    )

                    Text(
                        text = "${stringResource(id = R.string.price_icon)} ${
                            String.format(
                                "%.2f",
                                quotationRequestViewModel.getTotalPriceWithoutVat()
                            )
                        }",
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth(0.4f),
                        fontSize = fontSizeCost,
                        color = Color.Black,
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(35.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.summaryScreen_totalExclusiveBtw),
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth(0.6f),
                        fontSize = fontSizeCost,
                        color = Color.Black,
                    )

                    Text(
                        text = "${stringResource(id = R.string.price_icon)} ${
                            String.format(
                                "%.2f",
                                quotationRequestViewModel.getTotalVat()
                            )
                        }",
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth(0.4f),
                        fontSize = fontSizeCost,
                        color = Color.Black,
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 2.dp,
                    color = Color.Gray
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(35.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.summaryScreen_toPay),
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth(0.6f),
                        fontSize = fontSizeCost,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = "${stringResource(id = R.string.price_icon)} ${
                            String.format(
                                "%.2f",
                                quotationRequestViewModel.getTotalPriceWithoutVat() + quotationRequestViewModel.getTotalVat()
                            )
                        }",
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth(0.4f),
                        fontSize = fontSizeCost,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

    }
}