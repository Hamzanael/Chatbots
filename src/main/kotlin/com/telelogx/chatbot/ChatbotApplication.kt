package com.telelogx.chatbot

import com.github.cloudyrock.spring.v5.EnableMongock
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableMongock
class ChatbotApplication

fun main(args: Array<String>) {
    runApplication<ChatbotApplication>(*args)
}
