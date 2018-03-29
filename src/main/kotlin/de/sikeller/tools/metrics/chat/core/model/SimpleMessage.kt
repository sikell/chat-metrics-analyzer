package de.sikeller.tools.metrics.chat.core.model

import java.util.*

data class SimpleMessage(
    override val timestamp: Date,
    override val sender: Person,
    override var message: String
) : Message
