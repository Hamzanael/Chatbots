package com.telelogx.chatbot.api.response

import com.telelogx.chatbot.database.role.Role

data class UserResponse(
    val fullName: String,
    val email: String,
    val role: Role,
    val accountId: String,
    val enabled: Boolean
)