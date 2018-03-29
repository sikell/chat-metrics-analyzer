package de.sikeller.tools.metrics.chat.core

import de.sikeller.tools.metrics.chat.core.model.Chat

interface ChatTransformer {
    fun transform(input: String): Chat
}