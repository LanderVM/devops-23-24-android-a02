package com.example.templateapplication.model.adres

sealed interface ApiResponse<out T> { // TODO move generic to better place
    data class Success<out T>(val data: T) : ApiResponse<T>
    object Error : ApiResponse<Nothing>
    object Loading : ApiResponse<Nothing>
}