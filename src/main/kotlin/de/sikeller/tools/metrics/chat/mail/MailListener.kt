package de.sikeller.tools.metrics.chat.mail

import de.sikeller.tools.metrics.chat.mail.model.Mail
import org.springframework.util.StringUtils
import java.io.InputStream
import java.util.*
import javax.mail.Address
import javax.mail.Flags
import javax.mail.Folder
import javax.mail.Message
import javax.mail.Message.RecipientType
import javax.mail.MessagingException
import javax.mail.Multipart
import javax.mail.NoSuchProviderException
import javax.mail.Part
import javax.mail.Session
import javax.mail.internet.InternetAddress

class MailListener(
    private val protocol: String,
    private val host: String,
    private val port: String,
    private val userName: String,
    private val password: String
) {

    private val inbox = "INBOX"

    fun getNewEmails(): List<Mail> {
        val properties = getServerProperties(protocol, host, port)
        val session = Session.getDefaultInstance(properties)

        try {
            val store = session.getStore(protocol)
            store.connect(userName, password)

            val inbox = store.getFolder(inbox)
            inbox.open(Folder.READ_WRITE)

            val count = inbox.messageCount
            val messages = inbox.getMessages(1, count)
            val mails = messages
                .filter { !it.flags.contains(Flags.Flag.SEEN) }
                .map {
                    val fromAddresses = it.from
                    val content = it.content.toString()
                    Mail(
                        from = fromAddresses[0].toString(),
                        to = parseAddresses(it.getRecipients(RecipientType.TO)),
                        cc = parseAddresses(it.getRecipients(RecipientType.CC)),
                        subject = it.subject,
                        sentDate = it.sentDate.toString(),
                        message = content,
                        attachments = getAttachments(it)
                    )
                }

            inbox.close(false)
            store.close()
            return mails
        } catch (ex: NoSuchProviderException) {
            println("No provider for protocol: " + protocol)
            ex.printStackTrace()
            return emptyList()
        } catch (ex: MessagingException) {
            println("Could not connect to the message store")
            ex.printStackTrace()
            return emptyList()
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

    private fun parseAddresses(address: Array<Address>?): String =
        address
            ?.filter { it is InternetAddress }
            ?.map { it as InternetAddress }
            ?.fold("", { s, a -> s.plus(a.address) })
            ?: ""

    private fun getAttachments(message: Message): List<String> {
        val multipart = message.content as Multipart
        return (0 until multipart.count)
            .map(multipart::getBodyPart)
            .filter { bodyPart ->
                // only handle attachment message parts
                bodyPart.disposition != null
                    && bodyPart.disposition.toLowerCase() == Part.ATTACHMENT.toLowerCase()
                    && !StringUtils.isEmpty(bodyPart.fileName)
            }.map { bodyPart -> convertStreamToString(bodyPart.inputStream) }
    }

    private fun convertStreamToString(inputStream: InputStream): String {
        val s = Scanner(inputStream).useDelimiter("\\A")
        return if (s.hasNext()) s.next() else ""
    }
}
