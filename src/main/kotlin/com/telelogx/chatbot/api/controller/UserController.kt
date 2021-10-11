package com.telelogx.chatbot.api.controller

import com.telelogx.chatbot.api.response.UserResponse
import com.telelogx.chatbot.api.response.toResponse
import com.telelogx.chatbot.model.User
import com.telelogx.chatbot.service.UserService
import com.telelogx.chatbot.service.exceptions.NoEntityFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.ACCEPTED
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/{accountId}/users")
@CrossOrigin(origins = ["http://localhost:3000"])
class UserController {
    @Autowired
    lateinit var userService: UserService

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    fun getAllUsers(
        @PathVariable accountId: String
    ): List<UserResponse> = userService.getAll().map { it.toResponse() }


    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    fun registerUser(
        @PathVariable accountId: String, @RequestBody user: User
    ): ResponseEntity<UserResponse> {
        val registeredUser = userService.create(user.copy(accountId = accountId))
        return ResponseEntity(registeredUser.toResponse(), CREATED)
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    fun deleteUser(@PathVariable accountId: String, @PathVariable("id") id: String) {
        try {
            userService.delete(id)
        } catch (exception: NoEntityFoundException) {
        }
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    fun updateUser(@RequestBody user: User, @PathVariable id: String)
            : ResponseEntity<UserResponse> {
        val newUser = userService.update(user, id)
        return ResponseEntity(newUser.toResponse(), ACCEPTED)
    }


}