package com.example.templateapplication.model.formules

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FormulaViewModel : ViewModel() {
    private val _formuleUiState = MutableStateFlow(FormuleUiState())
    val formulaUiState = _formuleUiState.asStateFlow()

    fun updateDateRange(beginDate: Long?, endDate: Long?) {
        val begin = Calendar.getInstance()
        val end = Calendar.getInstance()

        if (beginDate != null) {
            begin.timeInMillis = beginDate
            if (endDate != null) end.timeInMillis = endDate else end.timeInMillis = beginDate
        }

        _formuleUiState.update {
            it.copy(startDate = begin, endDate = end)
        }
    }

    fun getDateRange(): String {
        if (_formuleUiState.value.startDate == null || _formuleUiState.value.endDate == null) {
            return "/"
        }
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)

        return "${dateFormat.format(_formuleUiState.value.startDate!!.timeInMillis)} - " +
                dateFormat.format(_formuleUiState.value.endDate!!.timeInMillis)
    }

    fun checkDate(): Boolean {
        return _formuleUiState.value.startDate != null && _formuleUiState.value.endDate != null
    }
}