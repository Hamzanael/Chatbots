package com.telelogx.chatbot.service.exceptions

class FailureAuthenticationException : Exception {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}