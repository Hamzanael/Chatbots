package com.telelogx.chatbot.service

import com.telelogx.chatbot.model.Role
import com.telelogx.chatbot.model.User
import com.telelogx.chatbot.model.repositry.UserRepository
import com.telelogx.chatbot.service.exceptions.DuplicatedEntityException
import com.telelogx.chatbot.service.exceptions.NoEntityFoundException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
internal class UserServiceTest {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRepository: UserRepository

    private val email = "test@telelogx.com"
    private val user = User("testUser", email, "12345678$", Role.USER, "1")


    @BeforeEach
    internal fun setUp() {
        userRepository.deleteAll()
    }


    @Test
    internal fun `user should be stored`() {

        userService.create(user)

        assertThat(userService.getAll()).usingElementComparatorIgnoringFields("_id", "password")
            .contains(user)
    }

    @Test
    internal fun `user should be deleted if exist`() {

        val userId = userService.create(user)

        userService.delete(userId._id)

        assertThat(userService.getAll()).isEmpty()
    }


    @Test
    internal fun `different users with same email should not be_registered`() {

        userService.create(user)

        assertThatThrownBy {
            userService.create(user)
        }.isInstanceOf(DuplicatedEntityException::class.java)
            .hasMessage("email with following is already exists test@telelogx.com")
    }

    @Test
    internal fun `delete unexciting user should throw an exception`() {
        assertThatThrownBy { userService.delete(email) }
            .isInstanceOf(NoEntityFoundException::class.java)
    }
}