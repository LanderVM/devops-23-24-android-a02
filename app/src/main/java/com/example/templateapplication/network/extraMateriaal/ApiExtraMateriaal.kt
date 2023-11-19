package com.example.templateapplication.network.extraMateriaal

import com.example.templateapplication.model.extraMateriaal.ExtraItemState
import kotlinx.serialization.Serializable

@Serializable
data class ApiExtraMateriaal(val id: Int,
                   val title: String,
                   val attributes: Array<String>,
                   val price: Double,
                   val stock: Int,
                   val initialAmount: Int,
                   ) {
}

data class ImageData(
    val imageUrl: String,
    val altText: String
)


fun List<ApiExtraMateriaal>.asDomainObjects(): List<ExtraItemState> {
    var domainList = this.map {
        ExtraItemState(it.id, it.title, it.attributes, it.price,it.stock, it.initialAmount)
    }
    return domainList
}