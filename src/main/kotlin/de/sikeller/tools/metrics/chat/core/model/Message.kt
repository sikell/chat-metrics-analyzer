package de.sikeller.tools.metrics.chat.core.model

import java.time.LocalDateTime

class Message(
        val timestamp: LocalDateTime,
        val sender: Person,
        val message: String
)