package de.sikeller.tools.metrics.chat.core

import de.sikeller.tools.metrics.chat.core.model.Chat
import de.sikeller.tools.metrics.chat.core.model.ChatMetric
import de.sikeller.tools.metrics.chat.core.model.CommunicationDensity
import de.sikeller.tools.metrics.chat.core.model.EmojiMetric
import de.sikeller.tools.metrics.chat.core.model.PersonMetric
import de.sikeller.tools.metrics.chat.core.model.TimeMetric
import de.sikeller.tools.metrics.chat.utils.EmojiHandler
import de.sikeller.tools.metrics.chat.utils.OperationResult
import de.sikeller.tools.metrics.chat.utils.TimeCalculation
import org.apache.commons.lang3.time.DateUtils
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.util.*

@Component
class MetricCalculatorImpl(val emojiHandler: EmojiHandler) : MetricCalculator {

    override fun calc(chat: Chat): OperationResult<ChatMetric> {
        return TimeCalculation().run {
            ChatMetric(
                personMetrics = personMetric(chat),
                timeMetric = timeMetric(chat)
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

    private fun timeMetric(chat: Chat): TimeMetric = TimeMetric(
        communicationDensity = CommunicationDensity(
            countPerDay = countPerDay(chat),
            countPerMonth = countPerMonth(chat),
            countPerYear = countPerYear(chat),
            countPerTimeHour = countPerTimeHour(chat)
        )
    )

    private fun countPerTimeHour(chat: Chat): Map<Int, Int> = chat.messages
        .groupingBy { m -> SimpleDateFormat("HH").format(m.timestamp).toInt() }
        .eachCount()
        .toMap()

    private fun countPerDay(chat: Chat): Map<Date, Int> = chat.messages
        .groupingBy { m ->
            DateUtils.truncate(m.timestamp, Calendar.DAY_OF_MONTH)
        }.eachCount()
        .toMap()

    private fun countPerMonth(chat: Chat): Map<Date, Int> = chat.messages
        .groupingBy { m ->
            DateUtils.truncate(m.timestamp, Calendar.MONTH)
        }.eachCount()
        .toMap()

    private fun countPerYear(chat: Chat): Map<Date, Int> = chat.messages
        .groupingBy { m ->
            DateUtils.truncate(m.timestamp, Calendar.YEAR)
        }.eachCount()
        .toMap()
}