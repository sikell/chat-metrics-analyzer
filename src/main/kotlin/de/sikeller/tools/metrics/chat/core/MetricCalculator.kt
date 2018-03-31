package de.sikeller.tools.metrics.chat.core

import de.sikeller.tools.metrics.chat.core.model.Chat
import de.sikeller.tools.metrics.chat.core.model.ChatMetric
import de.sikeller.tools.metrics.chat.utils.OperationResult

interface MetricCalculator {
    fun calc(chat: Chat): OperationResult<ChatMetric>
}