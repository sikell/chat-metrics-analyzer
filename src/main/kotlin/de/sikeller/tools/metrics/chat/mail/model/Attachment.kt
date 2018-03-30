package de.sikeller.tools.metrics.chat.mail.model

data class Attachment(
    val name: String,
    val contentType: String,
    val description: String?,
    val content: String
)