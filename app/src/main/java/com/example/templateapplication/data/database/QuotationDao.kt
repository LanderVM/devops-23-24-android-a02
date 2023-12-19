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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFormula(item: DbFormula)

    @Query("SELECT * FROM equipments")
    fun getEquipment(): Flow<List<DbEquipment>>

    @Query("SELECT * FROM formulas")
    fun getFormulas(): Flow<List<DbFormula>>
}