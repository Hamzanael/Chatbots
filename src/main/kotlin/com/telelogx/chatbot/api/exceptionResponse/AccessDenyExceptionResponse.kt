package com.telelogx.chatbot.api.exceptionResponse

import org.springframework.http.HttpStatus

data class AccessDenyExceptionResponse(val message: String, val status: HttpStatus)