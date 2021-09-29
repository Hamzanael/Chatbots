package com.telelogx.chatbot.service

import com.telelogx.chatbot.api.response.UserResponse
import com.telelogx.chatbot.database.extensions.toResponse
import com.telelogx.chatbot.database.model.User
import com.telelogx.chatbot.database.repositry.UserRepository
import com.telelogx.chatbot.exceptions.DuplicatedEntityException
import com.telelogx.chatbot.exceptions.NoEntityFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    private lateinit var userRepository: UserRepository

    fun create(user: User) {
        if (!userRepository.existsByEmail(user.email)) {
            userRepository.save(user)
        } else {
            throw DuplicatedEntityException("email is already exists")
        }

    }

    fun delete(email: String) {
        if (userRepository.existsByEmail(email)) {
            userRepository.deleteByEmail(email)
        } else {
            throw NoEntityFoundException("user does not exist")
        }
    }

    fun getAll(): List<UserResponse> {
        return userRepository.findAll()
            .map { it.toResponse() }
    }


}