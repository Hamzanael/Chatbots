package com.telelogx.chatbot.api

import com.telelogx.chatbot.api.response.UserResponse
import com.telelogx.chatbot.database.model.User
import com.telelogx.chatbot.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.ACCEPTED
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/{accountId}")
class UserApiController {
    @Autowired
    lateinit var userService: UserService


    @GetMapping
    fun getAllUsers(
        @PathVariable accountId: String
    ): List<UserResponse> {
        return userService.getAll()
    }


    @PostMapping
    fun registerUser(
        @PathVariable accountId: String, user: User
    ): HttpStatus {
        userService.create(user)
        return CREATED
    }

    @DeleteMapping
    fun deleteUser(@PathVariable accountId: String, @RequestParam("email") email: String): HttpStatus {
        userService.delete(email)
        return ACCEPTED
    }

}