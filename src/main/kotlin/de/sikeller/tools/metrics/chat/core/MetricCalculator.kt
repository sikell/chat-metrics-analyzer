package de.sikeller.tools.metrics.chat.core

import de.sikeller.tools.metrics.chat.core.model.Chat
import de.sikeller.tools.metrics.chat.core.model.ChatMetric

interface MetricCalculator {
    fun calc(chat: Chat): ChatMetric
}