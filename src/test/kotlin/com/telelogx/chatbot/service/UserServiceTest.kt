package com.telelogx.chatbot.service

import com.telelogx.chatbot.database.model.User
import com.telelogx.chatbot.database.repositry.UserRepository
import com.telelogx.chatbot.database.role.Role
import com.telelogx.chatbot.exceptions.ServiceException
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

    private val user = User("testUser", "test@telelogx.com", "123", Role.USER)

    @BeforeEach
    internal fun tearDown() {
        userRepository.deleteAll()
    }

    @Test
    internal fun `user should be stored`() {

        userService.create(user)

        assertThat(userService.getAll()).contains(user)
    }

    @Test
    internal fun `user should be deleted if exist`() {

        userService.create(user)

        userService.delete(user)

        assertThat(userService.getAll()).isEmpty()
    }

    @Test
    internal fun `different users with same email should not be_registered`() {

        userService.create(user)

        assertThatThrownBy {
            userService.create(user)
        }.isInstanceOf(ServiceException::class.java)
            .hasMessage("The user email is already exists")
    }


}