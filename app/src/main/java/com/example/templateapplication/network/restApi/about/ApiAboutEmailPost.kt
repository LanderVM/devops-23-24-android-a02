package com.example.templateapplication.network.restApi.about

import kotlinx.serialization.Serializable

@Serializable
data class ApiEmailPost(
    val email: String,
)

sealed interface PostEmailApiState {
    object Success : PostEmailApiState
    data class Error(val errorMessage: String) : PostEmailApiState
    object Loading : PostEmailApiState
}