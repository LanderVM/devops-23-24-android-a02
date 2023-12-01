package com.example.templateapplication.network.restApi

import com.example.templateapplication.model.extraMateriaal.ExtraItemState
import kotlinx.serialization.Serializable

@Serializable
data class QuotationEquipmentData(
    val equipment: List<ApiQuotationEquipment>,
    val totalAmount: Int
)

@Serializable
data class ApiQuotationEquipment(
    val id: Int,
    val title: String,
    val attributes: List<String>,
    val price: Double,
    val stock: Int,
    val imageData: QuotationImageData,
    val formulaIds: List<Int>
)

@Serializable
data class QuotationImageData(
    val imageUrl: String,
    val altText: String
)


fun QuotationEquipmentData.asDomainObjects(): List<ExtraItemState> {
    return this.equipment.map {
        ExtraItemState(it.id, it.title, it.attributes, it.price, it.stock, imageResourceId = 1)
    }
}