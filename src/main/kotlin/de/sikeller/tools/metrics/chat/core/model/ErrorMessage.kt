package de.sikeller.tools.metrics.chat.core.model

import java.util.*

data class ErrorMessage(override var message: String) : Message {
    override val sender: Person = Person("Error")
    override val timestamp: Date = Date()
}