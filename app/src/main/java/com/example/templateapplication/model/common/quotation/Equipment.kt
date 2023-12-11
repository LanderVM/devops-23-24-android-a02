package com.example.templateapplication.model.common.quotation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class Equipment(
    val extraItemId: Int,
    val title: String,
    val attributes: List<String>,
    val price: Double,
    val stock: Int,
    @DrawableRes val imageResourceId: Int = 0,
) {
    var amount by mutableIntStateOf(0)
    var isEditing by mutableStateOf(false)
}