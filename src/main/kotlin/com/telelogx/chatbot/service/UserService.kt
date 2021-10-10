package com.telelogx.chatbot.service

import com.telelogx.chatbot.model.User
import com.telelogx.chatbot.model.repositry.UserRepository
import com.telelogx.chatbot.security.toSecurityDetails
import com.telelogx.chatbot.service.exceptions.DuplicatedEntityException
import com.telelogx.chatbot.service.exceptions.NoEntityFoundException
import com.telelogx.chatbot.service.exceptions.WeakPasswordException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.regex.Pattern

@Service
class UserService : UserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    fun create(user: User): User {
        if (!userRepository.existsByEmail(user.email)) {
            checkThePasswordComplexity(user.password)
            return userRepository.insert(user.copy(password = passwordEncoder.encode(user.password)))
        } else {
            throw DuplicatedEntityException("email with following is already exists ${user.email}")
        }
    }

    fun delete(id: String): User {
        return withUser(id) {
            userRepository.delete(it)
            return@withUser it
        }
    }

    fun getAll(): List<User> {
        return userRepository.findAll()
    }

    fun getUserByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun getCurrentUser(): User {
        val user = userRepository.findByEmail(SecurityContextHolder.getContext().authentication.principal.toString())
        if (user != null) {
            return user
        } else throw NoEntityFoundException("user doesn't exist")
    }

    fun update(user: User, id: String): User {
        return withUser(id) {
            checkThePasswordComplexity(user.password)
            val updatedUser = user.copy(password = passwordEncoder.encode(user.password))
            updatedUser._id = id
            return@withUser userRepository.save(updatedUser)
        }
    }

    private fun withUser(id: String, dataBaseCRUDCommand: (user: User) -> User): User {
        val user: Optional<User> = userRepository.findById(id)
        if (user.isPresent) {
            return dataBaseCRUDCommand(user.get())
        } else {
            throw NoEntityFoundException("user does not exist")
        }
    }

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
        if (user == null) {
            throw NoEntityFoundException("this email $email is not registered")
        } else {
            return user.toSecurityDetails()
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

}




