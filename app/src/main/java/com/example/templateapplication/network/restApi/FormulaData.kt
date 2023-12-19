package com.example.templateapplication.network.restApi

import com.example.templateapplication.data.database.DbFormula
import kotlinx.serialization.Serializable

@Serializable
data class FormulaData(
    val id: Int,
    val title: String,
    val attributes: List<String>,
    val pricePerDayExtra: Double,
    val basePrice: List<Double>,
    val isActive: Boolean,
)

@Serializable
data class ApiGetFormula(
    val formulas: List<FormulaData>,
    val totalAmount: Int,
)

fun FormulaData.asDbFormula(): DbFormula = DbFormula(
    id = id,
    title = title,
    attributes = attributes,
    pricePerDayExtra = pricePerDayExtra,
    basePrice = basePrice,
    isActive = isActive,
)