package de.sikeller.tools.metrics.chat.utils

import com.fasterxml.jackson.annotation.JsonProperty

interface OperationResult<out A> {

    @JsonProperty("data")
    fun get(): A

    @JsonProperty("durationMillis")
    fun duration(): Long
}