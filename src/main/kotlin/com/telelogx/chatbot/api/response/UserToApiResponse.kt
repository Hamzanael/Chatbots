package com.telelogx.chatbot.api.response

import com.telelogx.chatbot.model.User

fun User.toResponse(): UserResponse {
    return UserResponse(
        this._id,
        this.fullName,
        this.email,
        this.role,
        this.accountId,
        this.isEnabled
    )
}
