package com.example.templateapplication.ui.screens.evenementpage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.R
import com.example.templateapplication.model.formules.FormuleViewModel
import com.example.templateapplication.ui.commons.ProgressieBar
import com.example.templateapplication.ui.commons.Titel
import com.example.templateapplication.ui.commons.VolgendeKnop
import com.example.templateapplication.ui.screens.evenementpage.components.AutoCompleteComponent
import java.text.SimpleDateFormat
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EvenementScreen(
    modifier: Modifier = Modifier,
    formuleViewModel: FormuleViewModel = viewModel(),
    navigateContactGegevensScreen: () -> Unit,
) {
    val formuleUiState by formuleViewModel.formuleUiState.collectAsState()
    val selectedStartDate = remember { mutableStateOf(formuleViewModel.beginDatum) }
    val selectedEndDate = remember { mutableStateOf(formuleViewModel.eindDatum) }

    val scrollState = rememberScrollState()

    val datumState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = selectedStartDate.value.timeInMillis,
        initialSelectedEndDateMillis = selectedEndDate.value.timeInMillis,
        yearRange = IntRange(start = 2023, endInclusive = Calendar.getInstance().get(Calendar.YEAR) + 1),
    )

    val beginTijdState = rememberTimePickerState(is24Hour = true, initialHour = 12, initialMinute = 0)
    val eindTijdState = rememberTimePickerState(is24Hour = true, initialHour = 12, initialMinute = 0)

    var buttonEnabled: Boolean = false

    // var invalidDates:LongArray = longArrayOf()

    // val validatorFunction:(Long)->Boolean = {datum:Long-> !LongStream.of(*invalidDates).anyMatch{n->n==datum}}

    if (datumState.selectedEndDateMillis == null || datumState.selectedStartDateMillis == null) {
        buttonEnabled = false
    } else {
        buttonEnabled = true
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProgressieBar(
            text = "evenement",
            progressie = 0.25f,
        )

        DatumPart(
            state = datumState,
            formuleViewModel = formuleViewModel,
            // validatorFunction = validatorFunction
        )
        Spacer(modifier = Modifier.height(20.dp))
        TimePart(state = beginTijdState, welkeTijd = "Begin tijd", formuleViewModel = formuleViewModel)
        Spacer(modifier = Modifier.height(20.dp))
        TimePart(state = eindTijdState, welkeTijd = "Eind tijd", formuleViewModel = formuleViewModel)
        Spacer(modifier = Modifier.height(35.dp))
        AutoCompleteComponent()
        Spacer(modifier = Modifier.height(35.dp))

        VolgendeKnop(
            navigeer = navigateContactGegevensScreen,
            enabled = buttonEnabled,
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}
fun getFormattedDate(timeInMillis: Long): String {
    val calender = Calendar.getInstance()
    calender.timeInMillis = timeInMillis
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    return dateFormat.format(calender.timeInMillis)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatumPart(
    modifier: Modifier = Modifier,
    state: DateRangePickerState,
    formuleViewModel: FormuleViewModel,
    // validatorFunction:(Long)->Boolean
) {
    Titel(
        text = "Datum",
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        LaunchedEffect(state.selectedStartDateMillis, state.selectedEndDateMillis) {
            formuleViewModel.updateDatums(state.selectedStartDateMillis, state.selectedEndDateMillis)
        }
        DateRangePicker(
            state,
            modifier = Modifier.height(450.dp),
            // dateFormatter = DatePickerFormatter("yy MM dd", "yy MM dd", "yy MM dd"),
            dateFormatter = DatePickerDefaults.dateFormatter("yy MM dd", "yy MM dd", "yy MM dd"),
            // dateValidator = validatorFunction,
            title = {
                Text(
                    text = "Selecteer begin dag tot eind dag evenement",
                    modifier = Modifier
                        .padding(16.dp),
                )
            },
            headline = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Box(Modifier.weight(1f)) {
                        (if (state.selectedStartDateMillis != null) state.selectedStartDateMillis?.let { getFormattedDate(it) } else "Start datum")?.let { Text(text = it) }
                    }
                    Box(Modifier.weight(1f)) {
                        (if (state.selectedEndDateMillis != null) state.selectedEndDateMillis?.let { getFormattedDate(it) } else "Eind datum")?.let { Text(text = it) }
                    }
                }
            },
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                containerColor = Color(android.graphics.Color.parseColor(stringResource(R.string.lichter))),
                titleContentColor = Color.Black,
                headlineContentColor = Color.Black,
                weekdayContentColor = Color.Black,
                subheadContentColor = Color.Black,
                yearContentColor = Color.Green,
                currentYearContentColor = Color.Red,
                selectedYearContainerColor = Color.Red,
                disabledDayContentColor = Color.Gray,
                todayDateBorderColor = Color(android.graphics.Color.parseColor(stringResource(R.string.main))),
                dayInSelectionRangeContainerColor = Color(android.graphics.Color.parseColor(stringResource(R.string.lichterder))),
                dayInSelectionRangeContentColor = Color.White,
                selectedDayContainerColor = Color(android.graphics.Color.parseColor(stringResource(R.string.main))),
            ),
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePart(
    modifier: Modifier = Modifier,
    state: TimePickerState,
    welkeTijd: String,
    formuleViewModel: FormuleViewModel,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = welkeTijd,
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
        )
        Spacer(modifier = Modifier.height(20.dp))
        TimeInput(
            state = state,
            colors = TimePickerDefaults.colors(
                periodSelectorSelectedContainerColor = Color(android.graphics.Color.parseColor(stringResource(R.string.main))),
                periodSelectorSelectedContentColor = Color(android.graphics.Color.parseColor(stringResource(R.string.wit))),
                timeSelectorSelectedContainerColor = Color(android.graphics.Color.parseColor(stringResource(R.string.main))),
                timeSelectorSelectedContentColor = Color(android.graphics.Color.parseColor(stringResource(R.string.wit))),
                timeSelectorUnselectedContainerColor = Color(android.graphics.Color.parseColor(stringResource(R.string.wit))),
                timeSelectorUnselectedContentColor = Color.Black,
            ),
        )
    }
}
