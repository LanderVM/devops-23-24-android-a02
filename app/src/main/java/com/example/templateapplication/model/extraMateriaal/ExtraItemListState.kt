package com.example.templateapplication.model.extraMateriaal

import com.example.templateapplication.model.common.quotation.Equipment

sealed interface ExtraItemDetailsApiState { // TODO rename
    object Success : ExtraItemDetailsApiState
    data class Error(val errorMessage: String) : ExtraItemDetailsApiState
    object Loading : ExtraItemDetailsApiState
}

data class EquipmentListState(val equipmentListState: List<Equipment> = listOf())