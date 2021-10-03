package com.telelogx.chatbot.api

import com.google.gson.Gson
import com.telelogx.chatbot.database.model.User
import com.telelogx.chatbot.database.role.Role.USER
import com.telelogx.chatbot.service.UserService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration
internal class UserControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var userService: UserService

    private val testUser = User(
        fullName = "testUser",
        email = "test@telelogx.com",
        password = "123456789$",
        role = USER,
        accountId = "1"
    )
    private val jsonUser: String = Gson().toJson(testUser)

    @BeforeEach
    internal fun setUp() {
        testUser._id = "615961813245c43f09273d6f"
        `when`(userService.update(testUser)).thenReturn(testUser._id)
        `when`(userService.delete(testUser.email)).thenReturn(testUser._id)
        `when`(userService.create(testUser)).thenReturn(testUser._id)
    }

    @Test
    @WithMockUser(username = "admin", roles = ["ADMIN"])
    fun `authorized user should get json list with all registered users`() {

        this.mockMvc.perform(get("/api/v1/1/users"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(APPLICATION_JSON))

        verify(userService).getAll()
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    fun `unauthorized user should throw an exception`() {

        val mvcResult = this.mockMvc.perform(get("/api/v1/1/users"))
            .andReturn()
        assertThat(mvcResult.response.contentAsString).contains("This user doesn't has authority to use this endpoint")


    }


    @Test
    @WithMockUser(username = "admin", roles = ["ADMIN"])
    fun `authorized user should register new user`() {

        val mvcResult = this.mockMvc.perform(
            post("/api/v1/1/users")
                .content(jsonUser)
                .contentType(APPLICATION_JSON)
        )
            .andExpect(status().isCreated).andReturn()

        assertThat(mvcResult.response.contentAsString).contains(testUser._id)
        verify(userService).create(testUser)
    }

    @Test
    @WithMockUser(username = "admin", roles = ["ADMIN"])
    fun `authorized user should edit old user`() {

        val mvcResult = this.mockMvc.perform(
            put("/api/v1/1/users")
                .content(jsonUser)
                .contentType(APPLICATION_JSON)
        )
            .andExpect(status().isAccepted)
            .andReturn()

        assertThat(mvcResult.response.contentAsString).contains(testUser._id)
        verify(userService).update(testUser)

    }

}