package de.sikeller.tools.metrics.chat.core.model

data class Chat(
    val messages: List<Message>,
    val persons: Set<Person>
)