package de.sikeller.tools.metrics.chat.mail

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("metrics.chat.mail")
class MailProperties {
    lateinit var server: String
    lateinit var user: String
    lateinit var password: String
    lateinit var port: String
    lateinit var protocol: String
}