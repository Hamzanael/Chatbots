package com.telelogx.chatbot.api.response

import com.telelogx.chatbot.model.Role

data class UserResponse(
    val id: String,
    val fullName: String,
    val email: String,
    val role: Role,
    val accountId: String,
    val enabled: Boolean
)