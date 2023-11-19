package com.example.templateapplication.model.formules

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Calendar

class FormuleViewModel : ViewModel() {
    private val _formuleUiState = MutableStateFlow(FormuleUiState())
    val formuleUiState = _formuleUiState.asStateFlow()

    fun updateDatums(begin: Long?, eind: Long?) {
        val calendarBegin = Calendar.getInstance()
        if (begin != null) {
            calendarBegin.timeInMillis = begin
        }

        val calendarEinde = Calendar.getInstance()
        if (eind != null) {
            calendarEinde.timeInMillis = eind
        }
        _formuleUiState.update {
            it.copy(beginDatum = calendarBegin, eindDatum = calendarEinde)
        }
    }

    fun getDatumsInString() : String {
        if (_formuleUiState.value.beginDatum == null || _formuleUiState.value.eindDatum == null) {
            return "/"
        }
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")

        return "${dateFormat.format(_formuleUiState.value.beginDatum!!.timeInMillis)} - " +
                "${dateFormat.format(_formuleUiState.value.eindDatum!!.timeInMillis)}"
    }

    // todo implement in evenementscreen
    fun updateBeginUur(uur: LocalTime) {
        _formuleUiState.update {
            it.copy(beginUur = uur)
        }
    }

    fun updateEindUur(uur: LocalTime) {
        _formuleUiState.update {
            it.copy(eindUur = uur)
        }
    }

    fun checkDate() : Boolean {
        return _formuleUiState.value.beginDatum != null && _formuleUiState.value.eindDatum != null
    }
}