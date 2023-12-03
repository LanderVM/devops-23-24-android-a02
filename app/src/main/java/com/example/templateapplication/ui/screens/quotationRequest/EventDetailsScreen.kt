package com.example.templateapplication.ui.screens.quotationRequest

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.R
import com.example.templateapplication.ui.commons.AddressTextField
import com.example.templateapplication.ui.commons.DateRangePicker
import com.example.templateapplication.ui.commons.DropDownSelect
import com.example.templateapplication.ui.commons.NextPageButton
import com.example.templateapplication.ui.commons.NumberOutlinedTextField
import com.example.templateapplication.ui.commons.ProgressieBar
import com.example.templateapplication.ui.commons.SeperatingTitle
import com.example.templateapplication.ui.screens.QuotationRequestViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsScreen(
    modifier: Modifier = Modifier,
    quotationRequestViewModel: QuotationRequestViewModel = viewModel(),
    navigateContactGegevensScreen: () -> Unit,
) {

    val scrollState = rememberScrollState()
    val requestState by quotationRequestViewModel.quotationRequestState.collectAsState()
    val uiState by quotationRequestViewModel.quotationUiState.collectAsState()

    var nextButtonEnabled by remember { mutableStateOf(false) }
    var recheckNextButtonStatus by remember { mutableStateOf(false) }


    val selectedStartDate by remember { mutableStateOf(requestState.startDate) }
    val selectedEndDate by remember { mutableStateOf(requestState.endDate) }
    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = selectedStartDate?.timeInMillis,
        initialSelectedEndDateMillis = selectedEndDate?.timeInMillis,
        yearRange = IntRange(
            start = 2023,
            endInclusive = Calendar.getInstance().get(Calendar.YEAR) + 1
        ),
        selectableDates = object : SelectableDates {

            override fun isSelectableDate(utcTimeMillis: Long): Boolean {

                // maak de dateformat aan, deze wordt gebruikt om de datum om te zetten naar millis
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                dateFormat.timeZone = TimeZone.getTimeZone("UTC")

                //blokeert alle dagen in het verleden
                val isBeforeToday = utcTimeMillis < System.currentTimeMillis()
                if (isBeforeToday) {
                    return false
                }

                // vervang dit door de data uit de api call
                val dateRanges = listOf(
                    Pair("2023-12-05 00:00:00", "2023-12-07 00:00:00"),
                    Pair("2023-12-10 00:00:00", "2023-12-12 00:00:00"),
                    Pair("2023-12-15 00:00:00", "2023-12-18 00:00:00")
                )
                //itereer door de lijst van dateRange en convert naar millisecondes
                for ((start, end) in dateRanges) {
                    val startMillis = dateFormat.parse(start)?.time
                    val endMillis = dateFormat.parse(end)?.time
                    if (startMillis != null && endMillis != null) {
                        if (utcTimeMillis in startMillis..endMillis) {
                            return false
                        }
                    }
                }
                return true
            }
        })

    LaunchedEffect(recheckNextButtonStatus) {
        nextButtonEnabled = quotationRequestViewModel.canNavigateNext()
        recheckNextButtonStatus = false
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProgressieBar(
            text = stringResource(id = R.string.eventDetails_progressbar),
            progression = 0.25f,
        )
        SeperatingTitle(
            text = stringResource(id = R.string.eventDetails_location_separator),
        )
        AddressTextField(
            showMap = true,
            placeResponse = requestState.placeResponse,
            apiStatus = quotationRequestViewModel.googleMapsApiState,
            hasFoundPlace = { quotationRequestViewModel.placeFound() },
            getPredictionsFunction = { quotationRequestViewModel.getPredictions() },
            updateInputFunction = {
                quotationRequestViewModel.updateInput(it)
                recheckNextButtonStatus = true
            },
            updateMarkerFunction = {
                quotationRequestViewModel.updateMarker()
            },
            googleMaps = uiState.googleMaps,
        )
        Spacer(modifier = Modifier.height(35.dp))
        DateRangePicker(
            state = dateRangePickerState,
            onSelectDateRange = { startDate, endDate ->
                quotationRequestViewModel.updateDateRange(startDate, endDate)
            },
            showCalenderToggle = false,
            enableRecheckFunction = { recheckNextButtonStatus = true }
        )
        SeperatingTitle(
            text = stringResource(id = R.string.eventDetails_details_separator),
        )
        NumberOutlinedTextField(
            label = stringResource(id = R.string.eventDetails_numberOfPeople),
            value = requestState.amountOfPeople,
            onValueChange = {
                quotationRequestViewModel.setAmountOfPeople(it)
                recheckNextButtonStatus = true
            },
        )
        if (requestState.selectedFormula != 1) {
            DropDownSelect(
                label = stringResource(id = R.string.eventDetails_beerType),
                isExpanded = uiState.dropDownExpanded,
                setExpanded = { quotationRequestViewModel.setDropDownExpanded(it) },
                dropDownOptions = uiState.beerDropDownOptions,
                selectedOption = if (requestState.wantsTripelBeer) 1 else 0,
                setSelectedOption = { quotationRequestViewModel.selectBeer(it) }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        NextPageButton(
            navigeer = navigateContactGegevensScreen,
            enabled = nextButtonEnabled,
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}
