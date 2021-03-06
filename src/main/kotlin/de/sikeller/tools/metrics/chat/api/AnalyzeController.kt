package de.sikeller.tools.metrics.chat.api

import de.sikeller.tools.metrics.chat.core.ChatTransformer
import de.sikeller.tools.metrics.chat.core.MetricCalculator
import de.sikeller.tools.metrics.chat.core.model.ChatMetric
import de.sikeller.tools.metrics.chat.persistence.dao.mail.EmailPersistenceService
import de.sikeller.tools.metrics.chat.utils.OperationResult
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("analyze")
class AnalyzeController(
    val transformer: ChatTransformer,
    val emailPersistenceService: EmailPersistenceService,
    val calculator: MetricCalculator
) {

    @PostMapping(value = ["/"], consumes = [MediaType.TEXT_PLAIN_VALUE])
    fun analyze(@RequestBody input: String): OperationResult<ChatMetric> = calculator.calc(transformer.transform(input))

    @GetMapping("/mail/{mailId}")
    fun analyzeUnreadMails(@PathVariable mailId: String): List<OperationResult<ChatMetric>> {
        val mail = emailPersistenceService.getMail(mailId)
        if (!mail.isPresent || mail.get().attachments.size != 1) return emptyList()
        return mail.get().attachments
            .filter { it.name.startsWith("WhatsApp Chat ") && it.name.endsWith(".txt") }
            .map { calculator.calc(transformer.transform(it.content)) }
    }
}