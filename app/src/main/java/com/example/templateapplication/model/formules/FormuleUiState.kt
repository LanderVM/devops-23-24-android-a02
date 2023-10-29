package com.example.templateapplication.model.formules

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Calendar

data class FormuleUiState(
    var beginDatum: Calendar = Calendar.getInstance(),
    var eindDatum: Calendar = Calendar.getInstance(),

    var beginUur: LocalTime? = null,
    var eindUur: LocalTime? = null,

    )