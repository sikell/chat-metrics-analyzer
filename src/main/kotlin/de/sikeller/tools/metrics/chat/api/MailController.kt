package de.sikeller.tools.metrics.chat.api

import de.sikeller.tools.metrics.chat.core.ChatTransformer
import de.sikeller.tools.metrics.chat.core.model.Chat
import de.sikeller.tools.metrics.chat.mail.MailListener
import de.sikeller.tools.metrics.chat.mail.model.Mail
import de.sikeller.tools.metrics.chat.persistence.dao.chat.ChatPersistenceService
import de.sikeller.tools.metrics.chat.persistence.dao.mail.EmailPersistenceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("process")
class MailController(
    val mailListener: MailListener,
    val transformer: ChatTransformer,
    val emailPersistenceService: EmailPersistenceService,
    val chatPersistenceService: ChatPersistenceService
) {

    @GetMapping("/inbox")
    fun unreadMails(): List<Mail> = mailListener.getNewEmails()
        .onEach { emailPersistenceService.saveMail(it) }

    @GetMapping("/mails")
    fun extractChats(): List<Chat> = emailPersistenceService.retrieveUnprocessedMails()
        .map { it.attachments }
        .flatMap { it }
        .filter { it.name.startsWith("WhatsApp Chat ") && it.name.endsWith(".txt") }
        .map { transformer.transform(it.content) }
        .onEach { chatPersistenceService.saveChat(it) }
}