package de.sikeller.tools.metrics.chat

import de.sikeller.tools.metrics.chat.mail.MailListener
import de.sikeller.tools.metrics.chat.mail.MailProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Config {

    @Bean
    fun mailListener(mailProperties: MailProperties): MailListener {
        println("Mail listener created")
        val mailListener = MailListener()
        mailListener.getNewEmails(
            mailProperties.protocol,
            mailProperties.server,
            mailProperties.port,
            mailProperties.user,
            mailProperties.password
        )
        return mailListener
    }
}