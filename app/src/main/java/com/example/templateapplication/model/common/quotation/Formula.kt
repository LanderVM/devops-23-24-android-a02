package com.example.templateapplication.model.common.quotation

data class Formula(
    val id: Int,
    val title: String,
    val attributes: List<String>,
    val pricePerDayExtra: Double,
    val basePrice: List<Double>,
    val isActive: Boolean,
)

sealed interface FormulaApiState {
    object Success : FormulaApiState
    data class Error(val errorMessage: String) : FormulaApiState
    object Loading : FormulaApiState
}

data class FormulaListState(val formulaListState: List<Formula> = listOf())