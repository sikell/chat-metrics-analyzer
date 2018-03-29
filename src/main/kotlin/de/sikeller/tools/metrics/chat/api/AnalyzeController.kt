package de.sikeller.tools.metrics.chat.api

import de.sikeller.tools.metrics.chat.core.ChatTransformer
import de.sikeller.tools.metrics.chat.core.model.Chat
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("analyze")
class AnalyzeController(val transformer: ChatTransformer) {

    @PostMapping(value= ["/"], consumes = [MediaType.TEXT_PLAIN_VALUE])
    fun analyze(@RequestBody input:String): Chat {
        return transformer.transform(input)
    }
}