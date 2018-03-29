package de.sikeller.tools.metrics.chat.api

import de.sikeller.tools.metrics.chat.core.ChatTransformer
import de.sikeller.tools.metrics.chat.core.MetricCalculator
import de.sikeller.tools.metrics.chat.core.model.ChatMetric
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("analyze")
class AnalyzeController(val transformer: ChatTransformer, val calculator: MetricCalculator) {

    @PostMapping(value = ["/"], consumes = [MediaType.TEXT_PLAIN_VALUE])
    fun analyze(@RequestBody input: String): ChatMetric {
        return calculator.calc(transformer.transform(input))
    }
}