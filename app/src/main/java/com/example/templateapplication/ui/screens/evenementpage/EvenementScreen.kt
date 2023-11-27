package com.example.templateapplication.ui.screens.evenementpage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.model.adres.EventAddressViewModel
import com.example.templateapplication.model.formules.FormulaViewModel
import com.example.templateapplication.ui.commons.AddressTextField
import com.example.templateapplication.ui.commons.DateRangePicker
import com.example.templateapplication.ui.commons.ProgressieBar
import com.example.templateapplication.ui.commons.SeperatingTitle
import com.example.templateapplication.ui.commons.NextPageButton
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EvenementScreen(
    modifier: Modifier = Modifier,
    formulaViewModel: FormulaViewModel = viewModel(),
    eventAddressViewModel: EventAddressViewModel = viewModel(),
    navigateContactGegevensScreen: () -> Unit,
) {
    val scrollState = rememberScrollState()
    var nextButtonEnabled by remember { mutableStateOf(false) }
    var recheckNextButtonStatus by remember { mutableStateOf(false) }
    val formulaUiState by formulaViewModel.formulaUiState.collectAsState()
    val selectedStartDate by remember { mutableStateOf(formulaUiState.startDate) }
    val selectedEndDate by remember { mutableStateOf(formulaUiState.endDate) }
    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = selectedStartDate?.timeInMillis,
        initialSelectedEndDateMillis = selectedEndDate?.timeInMillis,
        yearRange = IntRange(
            start = 2023,
            endInclusive = Calendar.getInstance().get(Calendar.YEAR) + 1
        ),
    )

    LaunchedEffect(recheckNextButtonStatus) {
        nextButtonEnabled = eventAddressViewModel.placeFound() && formulaViewModel.checkDate()
        recheckNextButtonStatus = false
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProgressieBar(
            text = "Evenement",
            progression = 0.25f,
        )
        SeperatingTitle(
            text = "Locatie",
        )
        AddressTextField(
            eventAddressViewModel = eventAddressViewModel,
            showMap = true,
            enableRecheckFunction = { recheckNextButtonStatus = true })
        Spacer(modifier = Modifier.height(35.dp))
        DateRangePicker(
            state = dateRangePickerState,
            formulaViewModel = formulaViewModel,
            showCalenderToggle = false,
            enableRecheckFunction = { recheckNextButtonStatus = true }
        )
        Spacer(modifier = Modifier.height(20.dp))
        NextPageButton(
            navigeer = navigateContactGegevensScreen,
            enabled = nextButtonEnabled,
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}
