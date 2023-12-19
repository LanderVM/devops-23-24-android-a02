package com.example.templateapplication.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.templateapplication.model.common.quotation.Formula

@Entity(tableName = "formulas")
data class DbFormula(
    @PrimaryKey val id: Int,
    val title: String,
    val attributes: List<String>,
    val pricePerDayExtra: Double,
    val basePrice: List<Double>,
    val isActive: Boolean,
//    @Embedded val imageData: DbImageData, TODO
)

fun DbFormula.asDomainObject(): Formula =
    Formula(
        id = id,
        title = title,
        attributes = attributes,
        pricePerDayExtra = pricePerDayExtra,
        basePrice = basePrice,
        isActive = isActive,
    )
fun List<DbFormula>.asDomainObjects() = map { it.asDomainObject() }