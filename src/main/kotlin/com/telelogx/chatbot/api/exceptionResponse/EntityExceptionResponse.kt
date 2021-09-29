package com.telelogx.chatbot.api.exceptionResponse

import org.springframework.http.HttpStatus
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class EntityExceptionResponse(
    val errorMessage: String,
    val httpStatus: HttpStatus,
    val time: String = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z").format(ZonedDateTime.now())
)