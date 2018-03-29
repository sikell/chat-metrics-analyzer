package de.sikeller.tools.metrics.chat.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("analyze")
class AnalyzeController {

    @GetMapping("/")
    fun analyze(): String {
        return "Hello world"
    }
}