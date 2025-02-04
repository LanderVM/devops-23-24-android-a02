package com.example.templateapplication.data.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.templateapplication.model.common.quotation.Equipment


@Entity(tableName = "equipments")
data class DbEquipment(
    @PrimaryKey val id: Int,
    val title: String,
    val attributes: List<String>,
    val price: Double,
    val stock: Int,
    @Embedded val imageData: DbImageData,
)

data class DbImageData(
    @ColumnInfo(name = "img_Url") val imageUrl: String,
    @ColumnInfo(name = "img_altText") val altText: String
)


fun DbEquipment.asDomainObject(): Equipment = Equipment(
    extraItemId = id,
    title = title,
    attributes = attributes,
    price = price,
    stock = stock,
    imgUrl = imageData.imageUrl,
    imgTxt = imageData.altText,
)

fun List<DbEquipment>.asDomainObjects() = map { it.asDomainObject() }