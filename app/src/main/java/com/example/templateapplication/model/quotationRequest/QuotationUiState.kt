package com.example.templateapplication.model.quotationRequest

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.templateapplication.model.common.googleMaps.GoogleMapsResponse
import com.example.templateapplication.model.common.quotation.DisabledDateRange
import com.example.templateapplication.ui.commons.DropDownOption

data class QuotationUiState(
    val dropDownExpanded: Boolean = false,
    val beerDropDownOptions: List<DropDownOption> = listOf(
        DropDownOption("Pils", 0),
        DropDownOption("Tripel", 1),
    ),
    val googleMaps: GoogleMapsResponse = GoogleMapsResponse(),
    val extraItems: List<ExtraItemState> = emptyList(),
    val listDateRanges: List<DisabledDateRange> = emptyList(),
)

data class ExtraItemState(
    val extraItemId: Int = 0,
    val title: String = "",
    val attributes: List<String> = emptyList(),
    val price: Double = 0.00,
    val stock: Int = 112,
    val imgUrl: String = "",
    val imgTxt: String = "",
) {
    var amount by mutableIntStateOf(0)
    var isEditing by mutableStateOf(false)
}

sealed interface DateRangesApiState {
    data class Success(val result: List<DisabledDateRange>) : DateRangesApiState
    data class Error(val errorMessage: String) : DateRangesApiState
    object Loading : DateRangesApiState
}
