package de.sikeller.tools.metrics.chat.mail.model

data class Mail(
    val from: String,
    val to: String,
    val cc: String,
    val subject: String,
    val sentDate: String,
    val message: String,
    val attachments: List<Attachment>
)