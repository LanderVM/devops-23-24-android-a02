package com.example.templateapplication.ui.screens.guideprice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.data.Datasource
import com.example.templateapplication.model.adres.EventAdresViewModel
import com.example.templateapplication.model.formules.FormuleViewModel
import com.example.templateapplication.ui.commons.AutoCompleteComponent
import com.example.templateapplication.ui.commons.DatumPart
import com.example.templateapplication.ui.commons.Titel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuidePriceScreen(
    modifier: Modifier = Modifier,
    formulaViewModel: FormuleViewModel = viewModel(),
    eventAdresViewModel: EventAdresViewModel = viewModel()
) {
    val formulasList = listOf("Basic", "All in", "Extended")
    val formulaUIState by formulaViewModel.formuleUiState.collectAsState()
    val selectedStartDate = remember { mutableStateOf(formulaUIState.beginDatum) }
    val selectedEndDate = remember { mutableStateOf(formulaUIState.eindDatum) }
    val scrollState = rememberScrollState()
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableIntStateOf(0) }

    val dataState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = selectedStartDate.value?.timeInMillis,
        initialSelectedEndDateMillis = selectedEndDate.value?.timeInMillis,
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
        DatumPart(
            state = dataState,
            formulaViewModel = formulaViewModel,
            showCalenderToggle = true,
        )
        Titel(
            text = "Locatie",
        )
        AutoCompleteComponent(eventAddressViewModel = eventAdresViewModel, showMap = false)
        Titel(
            text = "Details",
        )
        DropdownMenu(expanded, formulasList, selectedOption)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DropdownMenu(
    expanded: Boolean,
    formulasList: List<String>,
    selectedOption: Int
) {
    var expanded1 = expanded
    var selectedOption1 = selectedOption
    ExposedDropdownMenuBox(
        expanded = expanded1,
        onExpandedChange = { expanded1 = !expanded1 },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextField(
            readOnly = true,
            value = formulasList[selectedOption1],
            onValueChange = { },
            label = { Text("Formule") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded1
                )
            },
            modifier = Modifier.menuAnchor(),
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded1,
            onDismissRequest = {
                expanded1 = false
            }
        ) {
            formulasList.forEachIndexed { index, s ->
                DropdownMenuItem(text = { Text(s) }, onClick = {
                    selectedOption1 = index
                    expanded1 = !expanded1
                })
            }
        }
    }
}
