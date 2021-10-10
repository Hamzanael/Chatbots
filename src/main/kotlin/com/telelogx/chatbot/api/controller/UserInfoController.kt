package com.telelogx.chatbot.api.controller

import com.telelogx.chatbot.api.response.UserResponse
import com.telelogx.chatbot.api.response.toResponse
import com.telelogx.chatbot.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/auth/me")
class UserInfoController {
    @Autowired
    lateinit var userService: UserService

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN','ROLE_USER')")
    fun getUser(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): UserResponse {
        return userService.getCurrentUser().toResponse()
    }

}