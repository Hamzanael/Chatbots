package com.telelogx.chatbot.api.response

import com.telelogx.chatbot.database.model.User

fun User.toResponse(): UserResponse {
    return UserResponse(
        this.fullName, this.email,
        this.role,
        this.accountId,
        this.isEnabled
    )
}
