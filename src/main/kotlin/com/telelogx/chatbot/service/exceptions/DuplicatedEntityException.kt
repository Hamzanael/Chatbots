package com.telelogx.chatbot.service.exceptions

class DuplicatedEntityException : ServiceException {
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(message: String?) : super(message)
}