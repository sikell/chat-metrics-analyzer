package de.sikeller.tools.metrics.chat.core

import de.sikeller.tools.metrics.chat.core.model.Chat
import de.sikeller.tools.metrics.chat.core.model.ChatMetric
import de.sikeller.tools.metrics.chat.core.model.PersonMetric
import org.springframework.stereotype.Component

@Component
class MetricCalculatorImpl : MetricCalculator {
    override fun calc(chat: Chat): ChatMetric {
        return ChatMetric(
            personMetrics = personMetric(chat)
        )
    }

    private fun personMetric(chat: Chat): List<PersonMetric> {
        return chat.messages
            .groupingBy { m -> m.sender }
            .eachCount()
            .map { p ->
                PersonMetric(
                    person = p.key,
                    messageCount = p.value.toLong()
                )
            }
    }
}