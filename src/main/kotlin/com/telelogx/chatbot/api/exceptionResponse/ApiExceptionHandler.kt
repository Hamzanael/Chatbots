package com.telelogx.chatbot.api.exceptionResponse

import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(AccessDeniedException::class)
    fun unauthorizedToAccessEndPoint(throwable: Throwable): ResponseEntity<AccessDenyExceptionResponse> {
        return ResponseEntity(
            AccessDenyExceptionResponse(
                "This user doesn't has authority to use this endpoint",
                UNAUTHORIZED
            ),
            UNAUTHORIZED
        )
    }

}