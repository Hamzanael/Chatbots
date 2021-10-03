package com.telelogx.chatbot.security

import com.telelogx.chatbot.database.model.User

fun User.toSecurityDetails(): UserSecurityDetails {
    return UserSecurityDetails(
        this.email,
        this.password,
        this.role.name,
        this.isEnabled
    )
}