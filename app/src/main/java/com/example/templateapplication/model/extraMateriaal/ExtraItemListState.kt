package com.example.templateapplication.model.extraMateriaal

import com.example.templateapplication.model.quotationRequest.ExtraItemState

data class ExtraItemListState( // TODO move
    val currentExtraMateriaalList: List<ExtraItemState> = emptyList())

sealed interface ExtraItemDetailsApiState{
    object Success : ExtraItemDetailsApiState
    data class Error(val errorMessage: String): ExtraItemDetailsApiState
    object Loading : ExtraItemDetailsApiState
}