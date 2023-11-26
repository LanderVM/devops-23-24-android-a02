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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.model.adres.EventAddressViewModel
import com.example.templateapplication.model.formules.FormulaViewModel
import com.example.templateapplication.ui.commons.AutoCompleteComponent
import com.example.templateapplication.ui.commons.DateRangePicker
import com.example.templateapplication.ui.commons.ProgressieBar
import com.example.templateapplication.ui.commons.Titel
import com.example.templateapplication.ui.commons.VolgendeKnop
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EvenementScreen(
    modifier: Modifier = Modifier,
    formulaViewModel: FormulaViewModel = viewModel(),
    eventAddressViewModel: EventAddressViewModel = viewModel(factory = EventAddressViewModel.Factory),
    navigateContactGegevensScreen: () -> Unit,
) {
    val scrollState = rememberScrollState()

    val formulaUiState by formulaViewModel.formuleUiState.collectAsState()
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
        Titel(
            text = "Locatie",
        )
        AutoCompleteComponent(eventAddressViewModel = eventAddressViewModel, showMap = true)
        Spacer(modifier = Modifier.height(35.dp))
        DateRangePicker(
            state = dateRangePickerState,
            formulaViewModel = formulaViewModel,
            showCalenderToggle = false,
        )
        Spacer(modifier = Modifier.height(20.dp))
        VolgendeKnop(
            navigeer = navigateContactGegevensScreen,
            enabled = true //eventAdresViewModel.checkForPlace() /*&& formuleViewModel.checkDate()*/,
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}
