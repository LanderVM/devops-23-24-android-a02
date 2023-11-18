package com.example.templateapplication.model.formules

import java.time.LocalTime
import java.util.Calendar

data class FormuleUiState(
    val beginDatum: Calendar? = null,
    val eindDatum: Calendar? = null,

    val beginUur: LocalTime? = null,
    val eindUur: LocalTime? = null,

    )