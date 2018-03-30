package de.sikeller.tools.metrics.chat.mail

import java.util.*
import javax.mail.Address
import javax.mail.Flags
import javax.mail.Folder
import javax.mail.Message.RecipientType
import javax.mail.MessagingException
import javax.mail.NoSuchProviderException
import javax.mail.Session
import javax.mail.internet.InternetAddress

class MailListener {

    private val inbox = "INBOX"

    fun getNewEmails(protocol: String, host: String, port: String, userName: String, password: String) {
        val properties = getServerProperties(protocol, host, port)
        val session = Session.getDefaultInstance(properties)

        try {
            val store = session.getStore(protocol)
            store.connect(userName, password)

            val inbox = store.getFolder(inbox)
            inbox.open(Folder.READ_WRITE)

            val count = inbox.messageCount
            val messages = inbox.getMessages(1, count)
            for (message in messages) {
                if (!message.flags.contains(Flags.Flag.SEEN)) {
                    val fromAddresses = message.from
                    println("...................")
                    println("\t From: " + fromAddresses[0].toString())
                    println("\t To: " + parseAddresses(message.getRecipients(RecipientType.TO)))
                    println("\t CC: " + parseAddresses(message.getRecipients(RecipientType.CC)))
                    println("\t Subject: " + message.subject)
                    println("\t Sent Date:" + message.sentDate.toString())
                    try {
                        println(message.content)
                    } catch (ex: Exception) {
                        System.err.println("Error reading content !!")
                        ex.printStackTrace()
                    }
                }
            }

            inbox.close(false)
            store.close()
        } catch (ex: NoSuchProviderException) {
            println("No provider for protocol: " + protocol)
            ex.printStackTrace()
        } catch (ex: MessagingException) {
            println("Could not connect to the message store")
            ex.printStackTrace()
        }
    }

    private fun getServerProperties(protocol: String, host: String, port: String): Properties {
        val properties = Properties()
        properties.put(String.format("mail.%s.host", protocol), host)
        properties.put(String.format("mail.%s.port", protocol), port)
        properties.setProperty(String.format("mail.%s.socketFactory.class", protocol), "javax.net.ssl.SSLSocketFactory")
        properties.setProperty(String.format("mail.%s.socketFactory.fallback", protocol), "false")
        properties.setProperty(String.format("mail.%s.socketFactory.port", protocol), port)
        return properties
    }

    private fun parseAddresses(address: Array<Address>?): String? =
        address
            ?.filter { it is InternetAddress }
            ?.map { it as InternetAddress }
            ?.fold("", { s, a -> s.plus(a.address) })
            ?: ""
}
