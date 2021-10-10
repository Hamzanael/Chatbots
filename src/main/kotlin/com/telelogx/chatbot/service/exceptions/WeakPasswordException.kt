package com.telelogx.chatbot.service.exceptions

class WeakPasswordException : ServiceException {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}