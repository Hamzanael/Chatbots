package com.telelogx.chatbot.service

import com.telelogx.chatbot.api.response.UserResponse
import com.telelogx.chatbot.api.response.toResponse
import com.telelogx.chatbot.database.model.User
import com.telelogx.chatbot.database.repositry.UserRepository
import com.telelogx.chatbot.exceptions.DuplicatedEntityException
import com.telelogx.chatbot.exceptions.NoEntityFoundException
import com.telelogx.chatbot.exceptions.WeakPasswordException
import com.telelogx.chatbot.security.toSecurityDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.regex.Pattern

@Service
class UserService : UserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    fun create(user: User): String {
        if (!userRepository.existsByEmail(user.email)) {
            checkThePasswordComplexity(user.password)
            val registeredUser = userRepository.insert(user.copy(password = passwordEncoder.encode(user.password)))
            return registeredUser._id
        } else {
            throw DuplicatedEntityException("email with following is already exists ${user.email}")
        }
    }

    fun delete(email: String): String {
        return withUser(email) {
            userRepository.delete(it)
        }
    }

    fun getAll(): List<UserResponse> {
        return userRepository.findAll()
            .map { it.toResponse() }
    }

    fun update(user: User): String {
        return withUser(user.email) {
            checkThePasswordComplexity(user.password)
            val updatedUser = user.copy(password = passwordEncoder.encode(user.password));
            updatedUser._id = it._id
            userRepository.save(updatedUser)
        }
    }

    private fun withUser(email: String, dataBaseCRUDCommand: (user: User) -> Unit): String {
        val user: User? = userRepository.findByEmail(email)
        if (user != null) {
            dataBaseCRUDCommand(user)
            return user._id
        } else {
            throw NoEntityFoundException("user does not exist")
        }
    }

    private fun checkThePasswordComplexity(password: String) {
        val containsSpecialChar = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]").matcher(password).find()
        if (password.length < 8 || !containsSpecialChar) {
            throw WeakPasswordException(
                "The following password $password " +
                        "didn't meet the requirements which are " +
                        "at least 8 char long and contains one of the special characters"
            )
        }
    }

    override fun loadUserByUsername(emial: String): UserDetails {
        return userRepository.findByEmail(emial)!!.toSecurityDetails()
    }


}