package de.sikeller.tools.metrics.chat.api

import de.sikeller.tools.metrics.chat.mail.MailListener
import de.sikeller.tools.metrics.chat.mail.model.Mail
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("mail")
class MailController(val mailListener: MailListener) {

    @GetMapping("/")
    fun unreadMails(): List<Mail> = mailListener.getNewEmails()
}