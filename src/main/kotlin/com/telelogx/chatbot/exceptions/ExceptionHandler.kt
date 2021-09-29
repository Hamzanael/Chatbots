package com.telelogx.chatbot.exceptions

import com.telelogx.chatbot.api.exceptionResponse.EntityExceptionResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(ServiceException::class)
    fun entityException(entityException: ServiceException): ResponseEntity<EntityExceptionResponse> {
        return ResponseEntity<EntityExceptionResponse>(
            EntityExceptionResponse(
                entityException.message.toString(),
                HttpStatus.BAD_REQUEST
            ), HttpStatus.BAD_REQUEST
        )
    }


}