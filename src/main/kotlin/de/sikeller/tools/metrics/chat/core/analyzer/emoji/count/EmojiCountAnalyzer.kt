package de.sikeller.tools.metrics.chat.core.analyzer.emoji.count

import de.sikeller.tools.metrics.chat.core.analyzer.MessageAnalyzer
import de.sikeller.tools.metrics.chat.core.model.Message

class EmojiCountAnalyzer(
    val personEmojiCount: Map<String, Map<String, Int>> = HashMap()
) : MessageAnalyzer {

    override fun analyzeMessage(message: Message) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}