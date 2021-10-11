package com.telelogx.chatbot.model.databaseChangeLog

import com.github.cloudyrock.mongock.ChangeLog
import com.github.cloudyrock.mongock.ChangeSet
import com.telelogx.chatbot.model.Role.SUPER_ADMIN
import com.telelogx.chatbot.model.User
import com.telelogx.chatbot.model.repositry.UserRepository

@ChangeLog
class DatabaseChangeLog {
    @ChangeSet(order = "001", author = "Hamza", id = "initDB")
    fun initialDB(userRepository: UserRepository) {
        userRepository.save(
            User(
                "Admin",
                "admin@telelogx.com",
                "\$2a\$10\$9cnVs9k5u/r1/AXlfLaC2uLinqQCll8pK7LQiQy0yffRy7H0Plofu", //password: 123456789#
                SUPER_ADMIN,
                "1"
            )
        )
    }
}