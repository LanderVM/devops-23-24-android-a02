package com.example.templateapplication.network.restApi.quotationRequest

import android.annotation.SuppressLint
import com.example.templateapplication.data.database.DbEquipment
import com.example.templateapplication.data.database.DbImageData
import com.example.templateapplication.model.quotationRequest.ExtraItemState
import kotlinx.serialization.Serializable

@Serializable
data class QuotationEquipmentData(
    val equipment: List<ApiQuotationEquipment>,
    val totalAmount: Int // TODO remove from Api, web & here
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

fun ApiQuotationEquipment.asDbEquipment(): DbEquipment = DbEquipment(
    id = id,
    title = title,
    attributes = attributes,
    price = price,
    stock = stock,
    imageData = imageData.asDbImageData(),
)

@Serializable
data class QuotationImageData(
    val imageUrl: String,
    val altText: String
)

fun QuotationImageData.asDbImageData(): DbImageData = DbImageData( // TODO not needed
    imageUrl = imageUrl,
    altText = altText,
)

@SuppressLint("ResourceType")
fun QuotationEquipmentData.asDomainObjects(): List<ExtraItemState> {
    return this.equipment.map {
        ExtraItemState(it.id, it.title, it.attributes, it.price, it.stock, it.imageData.imageUrl, it.imageData.altText)
    }
}