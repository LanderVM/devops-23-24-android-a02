package com.example.templateapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbEquipment::class], version = 1)
abstract class BlancheDatabase : RoomDatabase() {
    abstract fun blancheDao(): QuotationDao
}