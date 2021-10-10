package com.telelogx.chatbot.api.response

import org.springframework.http.HttpStatus
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class ExceptionResponse(
    var errorMessage: String,
    val httpStatus: HttpStatus,
    val time: String = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z").format(ZonedDateTime.now())
)