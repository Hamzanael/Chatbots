package com.telelogx.chatbot.api

import com.telelogx.chatbot.api.response.UserResponse
import com.telelogx.chatbot.database.model.User
import com.telelogx.chatbot.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.ACCEPTED
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
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
class UserController {
    @Autowired
    lateinit var userService: UserService

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    fun getAllUsers(
        @PathVariable accountId: String
    ): List<UserResponse> = userService.getAll()


    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    fun registerUser(
        @PathVariable accountId: String, @RequestBody user: User
    ): ResponseEntity<Map<String, String>> {
        val userId = userService.create(user.copy(accountId = accountId))
        return ResponseEntity(mapOf("userId" to userId), CREATED)
    }

    @DeleteMapping("{email}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    fun deleteUser(@PathVariable accountId: String, @PathVariable("email") email: String)
            : ResponseEntity<Map<String, String>> {
        val userId = userService.delete(email)
        return ResponseEntity(mapOf("userId" to userId), ACCEPTED)
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    fun updateUser(@PathVariable accountId: String, @RequestBody user: User)
            : ResponseEntity<Map<String, String>> {
        val userId = userService.update(user)
        return ResponseEntity(mapOf("userId" to userId), ACCEPTED)
    }

}