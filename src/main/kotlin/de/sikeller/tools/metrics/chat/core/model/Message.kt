package de.sikeller.tools.metrics.chat.core.model

import java.util.*

open class Message(
        val timestamp: Date,
        val sender: Person,
        open var message: String
) {
    fun append(input: String) {
        message += input
    }
}

class ErrorMessage(override var message: String): Message(
        message = message,
        timestamp = Date(),
        sender = Person("Error")
)