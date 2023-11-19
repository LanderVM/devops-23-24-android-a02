package com.example.templateapplication.ui.screens.evenementpage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
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
import com.example.templateapplication.model.adres.EventAdresViewModel
import com.example.templateapplication.model.formules.FormuleViewModel
import com.example.templateapplication.ui.commons.AutoCompleteComponent
import com.example.templateapplication.ui.commons.DatumPart
import com.example.templateapplication.ui.commons.ProgressieBar
import com.example.templateapplication.ui.commons.VolgendeKnop
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EvenementScreen(
    modifier: Modifier = Modifier,
    formuleViewModel: FormuleViewModel = viewModel(),
    eventAdresViewModel: EventAdresViewModel = viewModel(factory = EventAdresViewModel.Factory),
    navigateContactGegevensScreen: () -> Unit,
) {
    val formuleUiState by formuleViewModel.formuleUiState.collectAsState()
    val selectedStartDate = remember { mutableStateOf(formuleUiState.beginDatum) }
    val selectedEndDate = remember { mutableStateOf(formuleUiState.eindDatum) }


    val scrollState = rememberScrollState()

    val datumState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = selectedStartDate.value?.timeInMillis,
        initialSelectedEndDateMillis = selectedEndDate.value?.timeInMillis,
        yearRange = IntRange(start = 2023, endInclusive = Calendar.getInstance().get(Calendar.YEAR) + 1),
    )

    val beginTijdState = rememberTimePickerState(is24Hour = true, initialHour = 12, initialMinute = 0)
    val eindTijdState = rememberTimePickerState(is24Hour = true, initialHour = 12, initialMinute = 0)

    // var invalidDates:LongArray = longArrayOf()

    // val validatorFunction:(Long)->Boolean = {datum:Long-> !LongStream.of(*invalidDates).anyMatch{n->n==datum}}

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
            formulaViewModel = formuleViewModel,
        )
        Spacer(modifier = Modifier.height(20.dp))
        TimePart(state = beginTijdState, welkeTijd = "Begin tijd", formuleViewModel = formuleViewModel)
        Spacer(modifier = Modifier.height(20.dp))
        TimePart(state = eindTijdState, welkeTijd = "Eind tijd", formuleViewModel = formuleViewModel)
        Spacer(modifier = Modifier.height(35.dp))
        AutoCompleteComponent(eventAdresViewModel = eventAdresViewModel)
        Spacer(modifier = Modifier.height(35.dp))

        VolgendeKnop(
            navigeer = navigateContactGegevensScreen,
            enabled = eventAdresViewModel.checkForPlace() /*&& formuleViewModel.checkDate()*/,
        )
        Spacer(modifier = Modifier.height(40.dp))
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
