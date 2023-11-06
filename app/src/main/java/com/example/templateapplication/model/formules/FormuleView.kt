package com.example.templateapplication.model.formules

import android.util.Log
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

    val beginDatum: Calendar
        get() = formuleUiState.value.beginDatum

    val eindDatum: Calendar
        get() = formuleUiState.value.eindDatum

    val beginUur: LocalTime?
        get() = formuleUiState.value.beginUur

    val eindUur: LocalTime?
        get() = formuleUiState.value.eindUur

    fun updateDatums(begin: Long?, eind: Long?) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val calendarBegin = Calendar.getInstance()
        if (begin != null) {
            calendarBegin.timeInMillis = begin
            Log.i("TSET", "${calendarBegin.time} ${beginDatum.time}")
        }

        val calendarEinde = Calendar.getInstance()
        if (eind != null) {
            calendarEinde.timeInMillis = eind
            Log.i("TSET", "${calendarEinde.time} ${eindDatum.time}")

        }
        _formuleUiState.update {
            it.copy(beginDatum = calendarBegin, eindDatum = calendarEinde)
        }
    }

    fun getDatumsInString() : String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        return "${dateFormat.format(beginDatum.timeInMillis)} - ${dateFormat.format(eindDatum.timeInMillis)}"
    }

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
}