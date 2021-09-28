package com.telelogx.chatbot.database.repositry

import com.telelogx.chatbot.database.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, String> {

    fun existsByEmail(email: String): Boolean
    fun deleteByEmail(email: String)

}