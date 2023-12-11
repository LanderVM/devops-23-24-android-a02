package com.example.templateapplication.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuotationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEquipment(item: DbEquipment)

    @Query("SELECT * FROM equipments")
    fun getEquipment(): Flow<List<DbEquipment>>
}