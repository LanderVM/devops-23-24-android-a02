package com.example.templateapplication.model.extraMateriaal

data class ExtraItemListState(
    val currentExtraMateriaalList: List<ExtraItemState> = emptyList())

sealed interface ExtraItemDetailsApiState{
    data class Success(val result: List<ExtraItemState>) : ExtraItemDetailsApiState
    data class Error(val errorMessage: String): ExtraItemDetailsApiState
    object Loading : ExtraItemDetailsApiState
}