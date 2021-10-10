package com.telelogx.chatbot.model.repositry

import com.telelogx.chatbot.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, String> {

    fun existsByEmail(email: String): Boolean
    fun deleteByEmail(email: String)
    fun findByEmail(email: String): User?
}