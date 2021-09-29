package com.telelogx.chatbot.exceptions

class NoEntityFoundException : ServiceException {
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(message: String?) : super(message)
}