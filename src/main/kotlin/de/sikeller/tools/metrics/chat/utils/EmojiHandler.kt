package de.sikeller.tools.metrics.chat.utils

import com.vdurmont.emoji.EmojiParser
import org.springframework.stereotype.Component

@Component
class EmojiHandler {

    fun findEmojis(input: String): List<String> = EmojiParser.extractEmojis(input)
}