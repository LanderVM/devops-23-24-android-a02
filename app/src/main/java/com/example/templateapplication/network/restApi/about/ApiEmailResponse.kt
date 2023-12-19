package com.example.templateapplication.network.restApi.about

import kotlinx.serialization.Serializable

@Serializable
data class ApiEmailResponse(
    val emailId: Int = 0,
)