package com.example.templateapplication.model.common.quotation

data class Formula( // TODO move && delete DataSource. Use repo instead
    val id: Int,
    val title: String,
    val listOfProperties: List<String>,
)
