package com.example.templateapplication.model.extraMateriaal

import com.example.templateapplication.model.common.quotation.Equipment

sealed interface EquipmentApiState {
    object Success : EquipmentApiState
    data class Error(val errorMessage: String) : EquipmentApiState
    object Loading : EquipmentApiState
}

data class EquipmentListState(val equipmentListState: List<Equipment> = listOf())