package com.example.templateapplication.network.extraMateriaal

import com.example.templateapplication.model.extraMateriaal.ExtraItemState
import kotlinx.serialization.Serializable

@Serializable
data class EquipmentData(
    val equipment: List<ApiExtraMateriaal>,
    val totalAmount: Int
)
@Serializable
data class ApiExtraMateriaal(
    val id: Int,
    val title: String,
    val attributes: List<String>,
    val price: Double,
    val stock: Int,
    val imageData: ImageData,
    val formulaIds: List<Int>
)
@Serializable
data class ImageData(
    val imageUrl: String,
    val altText: String
)



fun EquipmentData.asDomainObjects(): List<ExtraItemState> {
    var domainList = this.equipment.map {
        ExtraItemState(it.id, it.title, it.attributes, it.price,it.stock, imageResourceId = 1, )
    }
    return domainList
}