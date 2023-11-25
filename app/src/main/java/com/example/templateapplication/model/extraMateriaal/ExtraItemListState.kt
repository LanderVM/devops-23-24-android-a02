package com.example.templateapplication.model.extraMateriaal

data class ExtraItemListState(
    val currentExtraMateriaalList: List<ExtraItemState> = emptyList())

sealed interface ExtraMateriaalApiState{
    data class Success(val extraMateriaal: List<ExtraItemState>) : ExtraMateriaalApiState
    data class Error(val errorMessage: String): ExtraMateriaalApiState
    object Loading : ExtraMateriaalApiState
}