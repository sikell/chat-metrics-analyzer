package de.sikeller.tools.metrics.chat.core.analyzer

import de.sikeller.tools.metrics.chat.core.model.Message

interface MessageAnalyzer {

    fun analyzeMessage(message: Message)
}