package com.example.templateapplication.data


import com.example.templateapplication.model.extraMateriaal.ExtraItemState
import com.example.templateapplication.network.extraMateriaal.ExtraMateriaalApiService
import com.example.templateapplication.network.extraMateriaal.asDomainObjects

interface ExtraMateriaalRepository {
    suspend fun getExtraMateriaal(): List<ExtraItemState>
}

class ApiExtraMateriaalRepository(
    private val extraMateriaalApiService: ExtraMateriaalApiService
): ExtraMateriaalRepository{
    override suspend fun getExtraMateriaal(): List<ExtraItemState> {
        return extraMateriaalApiService.getExtraMateriaal().asDomainObjects()
    }

}