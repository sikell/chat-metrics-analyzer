package de.sikeller.tools.metrics.chat.core.model

import java.util.*

interface Message {
    val timestamp: Date
    val sender: Person
    var message: String

    fun append(messagePart: String) {
        message += messagePart
    }
}