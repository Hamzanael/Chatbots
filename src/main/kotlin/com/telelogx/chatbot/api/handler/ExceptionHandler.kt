package com.telelogx.chatbot.api.handler

import com.telelogx.chatbot.api.response.ExceptionResponse
import com.telelogx.chatbot.service.exceptions.ServiceException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(ServiceException::class)
    fun entityException(entityException: ServiceException): ResponseEntity<ExceptionResponse> {
        return ResponseEntity<ExceptionResponse>(
            ExceptionResponse(
                entityException.message.toString(),
                HttpStatus.BAD_REQUEST
            ), HttpStatus.BAD_REQUEST
        )
    }


}