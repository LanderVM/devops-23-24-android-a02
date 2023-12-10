package com.example.templateapplication.model.common.quotation

data class Equipment(
    val extraItemId: Int,
    val title: String,
    val attributes: List<String>,
    val price: Double,
    val stock: Int,
)