package de.sikeller.tools.metrics.chat.core.model

data class PersonMetric(
    val person: Person,
    val messageCount: Long,
    val messageLength: Long,
    val emojiMetric: List<EmojiMetric>
)