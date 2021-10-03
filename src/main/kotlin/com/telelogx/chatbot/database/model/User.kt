package com.telelogx.chatbot.database.model

import com.telelogx.chatbot.database.role.Role
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
    val fullName: String,
    @Indexed(unique = true) val email: String,
    val password: String,
    val role: Role,
    val accountId: String,
    val isEnabled: Boolean = true,
) {
    @Id
    lateinit var _id: String
}

