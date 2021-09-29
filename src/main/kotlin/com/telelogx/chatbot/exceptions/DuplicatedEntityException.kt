package com.telelogx.chatbot.exceptions

class DuplicatedEntityException : ServiceException {
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(message: String?) : super(message)
}