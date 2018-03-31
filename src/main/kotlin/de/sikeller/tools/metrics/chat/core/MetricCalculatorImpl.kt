package de.sikeller.tools.metrics.chat.core

import de.sikeller.tools.metrics.chat.core.model.Chat
import de.sikeller.tools.metrics.chat.core.model.ChatMetric
import de.sikeller.tools.metrics.chat.core.model.EmojiMetric
import de.sikeller.tools.metrics.chat.core.model.PersonMetric
import de.sikeller.tools.metrics.chat.utils.EmojiHandler
import de.sikeller.tools.metrics.chat.utils.OperationResult
import de.sikeller.tools.metrics.chat.utils.TimeCalculation
import org.springframework.stereotype.Component

@Component
class MetricCalculatorImpl(val emojiHandler: EmojiHandler) : MetricCalculator {

    override fun calc(chat: Chat): OperationResult<ChatMetric> {
        return TimeCalculation().run {
            ChatMetric(
                personMetrics = personMetric(chat)
            )
        }
    }

    private fun personMetric(chat: Chat): List<PersonMetric> {
        val emojiMetric = emojiMetric(chat)
        return chat.messages
            .groupingBy { m -> m.sender }
            .eachCount()
            .map { p ->
                PersonMetric(
                    person = p.key,
                    messageCount = p.value.toLong(),
                    emojiMetric = emojiMetric[p.key.name] ?: emptyList()
                )
            }
    }

    private fun emojiMetric(chat: Chat): Map<String, List<EmojiMetric>> {
        val personMessages = chat.messages
            .groupBy({ m -> m.sender }, { m -> m.message })

        val concatMessages = personMessages
            .mapValuesTo(HashMap(), { e ->
                e.value
                    .fold("", { s, i -> s + i })
            })

        return concatMessages
            .mapKeys { m -> m.key.name }
            .mapValues { m ->
                emojiHandler.findEmojis(m.value)
                    .groupingBy { it }
                    .eachCount()
                    .map { p ->
                        EmojiMetric(
                            emoji = p.key,
                            count = p.value
                        )
                    }.sortedByDescending(EmojiMetric::count)
            }
    }
}