package de.sikeller.tools.metrics.chat.api

import de.sikeller.tools.metrics.chat.core.ChatTransformer
import de.sikeller.tools.metrics.chat.core.MetricCalculator
import de.sikeller.tools.metrics.chat.core.model.ChatMetric
import de.sikeller.tools.metrics.chat.mail.MailListener
import de.sikeller.tools.metrics.chat.mail.model.Mail
import de.sikeller.tools.metrics.chat.utils.OperationResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("mail")
class MailController(val mailListener: MailListener, val transformer: ChatTransformer, val calculator: MetricCalculator) {

    @GetMapping("/")
    fun unreadMails(): List<Mail> = mailListener.getNewEmails()

    @GetMapping("/analyze")
    fun analyzeUnreadMails(): List<OperationResult<ChatMetric>> = mailListener.getNewEmails()
        .filter { it.attachments.size == 1 }
        .flatMap { it.attachments }
        .filter { it.name.startsWith("WhatsApp Chat ") && it.name.endsWith(".txt") }
        .map { calculator.calc(transformer.transform(it.content)) }
}