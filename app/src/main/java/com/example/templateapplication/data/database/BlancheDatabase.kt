package com.example.templateapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Database(entities = [DbEquipment::class], version = 1)
@TypeConverters(Converters::class)
abstract class BlancheDatabase : RoomDatabase() {
    abstract fun quotationDao(): QuotationDao
}

class Converters {
    @TypeConverter
    fun fromList(value : List<String>) = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<String>>(value)
}
