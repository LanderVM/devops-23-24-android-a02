package com.example.templateapplication.model.formules

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Calendar

data class FormuleUiState(
    val beginDatum: Calendar = Calendar.getInstance(),
    val eindDatum: Calendar = Calendar.getInstance(),

    val beginUur: LocalTime? = null,
    val eindUur: LocalTime? = null,

    )