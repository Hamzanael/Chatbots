package com.telelogx.chatbot.service

import com.telelogx.chatbot.database.model.User
import com.telelogx.chatbot.database.repositry.UserRepository
import com.telelogx.chatbot.exceptions.ServiceException
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
            throw ServiceException("The user email is already exists")
        }

    }

    fun delete(user: User) {
        if (userRepository.existsByEmail(user.email)) {
            userRepository.deleteByEmail(user.email)
        } else {
            throw ServiceException("The user you want to delete is not exist")
        }
    }

    fun getAll(): List<User> {
        return userRepository.findAll()
    }


}