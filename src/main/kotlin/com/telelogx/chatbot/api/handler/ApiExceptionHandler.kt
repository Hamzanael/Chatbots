package com.telelogx.chatbot.api.handler

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.telelogx.chatbot.api.response.ExceptionResponse
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(AccessDeniedException::class)
    fun unauthorizedToAccessEndPoint(throwable: Throwable): ResponseEntity<ExceptionResponse> {
        return ResponseEntity(
            ExceptionResponse(
                "This user doesn't has authority to use this endpoint",
                UNAUTHORIZED
            ),
            UNAUTHORIZED
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class, MissingKotlinParameterException::class)
    fun validationExceptions(throwable: Throwable): ResponseEntity<ExceptionResponse> {
        return ResponseEntity(
            ExceptionResponse(
                "Some of the fields are missing please check them again: ${throwable.cause}",
                HttpStatus.BAD_REQUEST
            ), HttpStatus.BAD_REQUEST
        )
    }

}