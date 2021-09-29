package com.telelogx.chatbot.database.extensions

import com.telelogx.chatbot.api.response.UserResponse
import com.telelogx.chatbot.database.model.User

fun User.toResponse(): UserResponse {
    return UserResponse(this.fullName, this.email, this.role)
}