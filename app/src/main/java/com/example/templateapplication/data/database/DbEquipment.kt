package com.example.templateapplication.data.database

import androidx.room.Entity
import com.example.templateapplication.network.restApi.QuotationImageData

@Entity
data class DbEquipment(
    val id: Int,
    val title: String,
    val attributes: List<String>,
    val price: Double,
    val stock: Int,
    val imageData: QuotationImageData,
    val formulaIds: List<Int> // TODO what is this? & why does api give this?
)