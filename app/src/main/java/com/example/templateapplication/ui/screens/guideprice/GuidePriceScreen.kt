package com.example.templateapplication.ui.screens.guideprice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.data.Datasource
import com.example.templateapplication.model.formules.FormuleViewModel
import com.example.templateapplication.ui.commons.AutoCompleteComponent
import com.example.templateapplication.ui.commons.DatumPart
import com.example.templateapplication.ui.commons.ProgressieBar
import com.example.templateapplication.ui.commons.VolgendeKnop
import com.example.templateapplication.ui.screens.evenementpage.TimePart
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuidePriceScreen(
    modifier: Modifier = Modifier,
    formulaViewModel: FormuleViewModel = viewModel()
) {
    val formulasList = Datasource().loadFormules()
    val formuleUiState by formulaViewModel.formuleUiState.collectAsState()
    val scrollState = rememberScrollState()

    val datumState = rememberDateRangePickerState(
        yearRange = IntRange(start = 2023, endInclusive = Calendar.getInstance().get(Calendar.YEAR) + 1),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DatumPart(
            state = datumState,
            formulaViewModel = formulaViewModel,
        )
        Spacer(modifier = Modifier.height(35.dp))
//        AutoCompleteComponent(eventAdresViewModel = eventAdresViewModel)
        Spacer(modifier = Modifier.height(35.dp))

    }
}
