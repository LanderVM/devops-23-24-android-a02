package com.example.templateapplication.model

sealed interface ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>
    object Error : ApiResponse<Nothing>
    object Loading : ApiResponse<Nothing>
}