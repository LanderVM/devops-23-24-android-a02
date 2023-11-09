package com.example.templateapplication.model.extraMateriaal

import android.graphics.drawable.AnimatedImageDrawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ExtraMateriaalItem(
    val title: String,
    val category: String,
    val price: Double,
    val amount: Int,
    @DrawableRes val imageResourceId: Int
)