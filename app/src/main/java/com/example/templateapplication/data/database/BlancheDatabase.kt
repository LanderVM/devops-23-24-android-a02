package com.example.templateapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Database(entities = [DbEquipment::class, DbFormula::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BlancheDatabase : RoomDatabase() {
    abstract fun quotationDao(): QuotationDao
}

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromDoubleList(value: List<Double>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toDoubleList(value: String): List<Double> {
        return Json.decodeFromString(value)
    }
}
