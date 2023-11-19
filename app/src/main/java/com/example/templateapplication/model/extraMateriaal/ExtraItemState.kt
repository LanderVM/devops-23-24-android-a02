package com.example.templateapplication.model.extraMateriaal

import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class ExtraItemState(
    val extraItemId: Int = 0,
    val title: String = "",
    val attributes: Array<String> = emptyArray(),
    val price: Double = 0.00,
    val stock: Int = 112,
    val initialAmount: Int = 1,
    @DrawableRes val imageResourceId: Int = 0,

){
    var amount by mutableIntStateOf(initialAmount)
    var isEditing by mutableStateOf(false)
}