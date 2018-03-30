package de.sikeller.tools.metrics.chat.core

import de.sikeller.tools.metrics.chat.core.model.Chat
import de.sikeller.tools.metrics.chat.core.model.ErrorMessage
import de.sikeller.tools.metrics.chat.core.model.Message
import de.sikeller.tools.metrics.chat.core.model.Person
import de.sikeller.tools.metrics.chat.core.model.SimpleMessage
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

@Component
class WhatsAppChatTransformer : ChatTransformer {

    override fun transform(input: String): Chat {
        val lines = input.lines()
        val messageRegex = "^(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2}:\\d{2}):\\s([^:]+):\\s(.+)"
        val pattern = Pattern.compile(messageRegex)
        val messages = lines.fold(LinkedList<Message>(), { list, line ->
            val matcher = pattern.matcher(line)
            if (!matcher.matches()) {
                // this could be a next line message
                if (list.size == 0) {
                    list.add(ErrorMessage(line))
                    list
                } else {
                    val last = list.last
                    last.append("\n" + line)
                    list
                }
            } else {
                val timestamp = matcher.group(1)
                val person = matcher.group(2)
                val message = matcher.group(3)
                val parsedTimestamp = SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(timestamp)
                list.add(
                    SimpleMessage(
                        parsedTimestamp,
                        Person(person),
                        message
                    )
                )
                list
            }
        })
        return Chat(
            messages,
            messages.map { m -> m.sender }.toSet()
        )
    }
}