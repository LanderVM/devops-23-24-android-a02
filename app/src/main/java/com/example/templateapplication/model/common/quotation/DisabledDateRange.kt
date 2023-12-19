package com.example.templateapplication.model.common.quotation

import java.time.Instant


data class DisabledDateRange(
    val startTime: Instant,
    val endTime: Instant,
)