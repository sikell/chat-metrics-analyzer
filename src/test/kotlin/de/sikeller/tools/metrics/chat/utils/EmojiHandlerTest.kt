package de.sikeller.tools.metrics.chat.utils

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class EmojiHandlerTest : StringSpec() {

    private val emojiHandler: EmojiHandler = EmojiHandler()

    init {
        "a string without emojis should return an empty list" {
            emojiHandler.findEmojis("What a nice test string!") shouldBe emptyList()
        }
        "a string with whitespace separated emojis should return an list with them" {
            emojiHandler.findEmojis("This 🤔 is a good one 😂") shouldBe listOf("🤔", "😂")
        }
        "a string with whitespace separated emojis in unicode should return an list with them" {
            emojiHandler.findEmojis("This \uD83E\uDD14 is a good one \uD83D\uDE44") shouldBe listOf("🤔", "🙄")
        }
        "a string with not separated emojis should return an list with them" {
            emojiHandler.findEmojis("😂🤔 is a good one 😂\uD83D\uDE02") shouldBe listOf("😂", "🤔", "😂", "😂")
        }
    }
}