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
import java.time.Instant
import java.util.Calendar

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


    val selectedStartDate by remember { mutableStateOf(requestState.startTime) }
    val selectedEndDate by remember { mutableStateOf(requestState.endTime) }
    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = selectedStartDate?.timeInMillis,
        initialSelectedEndDateMillis = selectedEndDate?.timeInMillis,
        yearRange = IntRange(
            start = 2023,
            endInclusive = Calendar.getInstance().get(Calendar.YEAR) + 1
        ),
        selectableDates = object : SelectableDates {

            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val isTimeInPast = utcTimeMillis < System.currentTimeMillis()
                if (isTimeInPast) return false

                for (item in uiState.listDateRanges)
                        if (Instant.ofEpochMilli(utcTimeMillis) in item.startTime..item.endTime)
                            return false

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
            placeResponse = uiState.googleMaps.eventAddressAutocompleteCandidates,
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
            value = requestState.numberOfPeople,
            onValueChange = {
                quotationRequestViewModel.setAmountOfPeople(it)
                recheckNextButtonStatus = true
            },
        )
        if (requestState.formulaId != 1) {
            DropDownSelect(
                label = stringResource(id = R.string.eventDetails_beerType),
                isExpanded = uiState.dropDownExpanded,
                setExpanded = { quotationRequestViewModel.setDropDownExpanded(it) },
                dropDownOptions = uiState.beerDropDownOptions,
                selectedOption = if (requestState.isTripelBier) 1 else 0,
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
