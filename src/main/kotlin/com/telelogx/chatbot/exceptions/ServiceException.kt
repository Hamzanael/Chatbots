package com.telelogx.chatbot.exceptions

open class ServiceException : Exception {
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(message: String?) : super(message)
}
