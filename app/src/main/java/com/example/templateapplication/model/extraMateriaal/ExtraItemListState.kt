package com.example.templateapplication.model.extraMateriaal

sealed interface ExtraItemDetailsApiState { // TODO rename
    object Success : ExtraItemDetailsApiState
    data class Error(val errorMessage: String) : ExtraItemDetailsApiState
    object Loading : ExtraItemDetailsApiState
}